package com.mibe.iot.thinker.discovery.adapter.from.ble

import com.mibe.iot.thinker.constants.PROFILE_DEFAULT
import com.mibe.iot.thinker.constants.PROFILE_PROD
import com.mibe.iot.thinker.discovery.application.port.from.ConnectDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.application.port.from.ControlDeviceDiscoveryPort
import com.mibe.iot.thinker.discovery.application.port.from.GetDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.domain.DeviceConnectionData
import com.mibe.iot.thinker.discovery.domain.DiscoveredDevice
import com.welie.blessed.BluetoothCentralManager
import com.welie.blessed.BluetoothCentralManagerCallback
import com.welie.blessed.BluetoothPeripheral
import com.welie.blessed.ScanResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import javax.annotation.PostConstruct
import kotlin.collections.HashMap


@Component
@Profile(PROFILE_PROD, PROFILE_DEFAULT)
class DeviceDiscoveryHandler
@Autowired constructor(
    private val bleDiscoveryDataHolder: BleDiscoveryDataHolder,
    private val blePeripheralCallback: BlePeripheralCallback
) : GetDiscoveredDevicePort, ControlDeviceDiscoveryPort, ConnectDiscoveredDevicePort {
    private val log = KotlinLogging.logger {}

    @Value("\${thinker.ble.rssiThreshold:-120}")
    private lateinit var rssiThreshold: String

    private lateinit var central: BluetoothCentralManager
    private val isActive = AtomicBoolean(false)
    private val discoveryRequestsAmount = AtomicInteger(0)

    @PostConstruct
    private fun initCentralManager() {
        val bleCentralCallback = object : BluetoothCentralManagerCallback() {
            override fun onDiscoveredPeripheral(peripheral: BluetoothPeripheral, scanResult: ScanResult) {
                val address = peripheral.address
                log.trace { "Discovered device: address=$address name=${peripheral.name} uuids=${peripheral.device?.uuids}" }
                bleDiscoveryDataHolder.noticedDevices[address] = Pair(peripheral, LocalDateTime.now())
                bleDiscoveryDataHolder.run {
                    if (isAllowedToConnect(address) && !isDiscovered(address)) {
                        central.connectPeripheral(peripheral, blePeripheralCallback)
                    }
                }
            }
        }
        central = BluetoothCentralManager(bleCentralCallback)
        central.setRssiThreshold(rssiThreshold.toInt())
    }

    override suspend fun startDiscovery() {
        isActive.set(true)
        discoveryRequestsAmount.incrementAndGet()
        central.scanForPeripherals()
    }

    override fun stopDiscovery(gracefully: Boolean) {
        val requestsAmount = discoveryRequestsAmount.decrementAndGet()
        if (!gracefully || requestsAmount <= 0) {
            discoveryRequestsAmount.set(0)
            central.stopScan()
            isActive.set(false)
        }
    }

    override fun isDiscovering(): Boolean {
        return isActive.get()
    }

    override suspend fun getDiscoveredDevices(): Flow<DiscoveredDevice> {
        return bleDiscoveryDataHolder.noticedDevices.values.map { it.first.toDiscoveredDevice(discoveredAt = it.second) }
            .asFlow()
    }

    override suspend fun getConnectedDeviceByAddress(address: String): DiscoveredDevice? {
        return bleDiscoveryDataHolder.noticedDevices[address]?.let {
            it.first.toDiscoveredDevice(it.second)
        }
    }

    override suspend fun connectDevice(connectionData: DeviceConnectionData) {
        bleDiscoveryDataHolder.apply {
            deviceConnectionData[connectionData.address] = connectionData
            allowedAddresses += connectionData.address
        }
    }

    override suspend fun reconnectDevice(connectionData: DeviceConnectionData) {
        bleDiscoveryDataHolder.devicesToReconnect += connectionData.address
        connectDevice(connectionData)
    }
}