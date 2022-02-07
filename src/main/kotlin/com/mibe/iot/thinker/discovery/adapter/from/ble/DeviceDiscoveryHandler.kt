package com.mibe.iot.thinker.discovery.adapter.from.ble

import com.mibe.iot.thinker.discovery.application.port.from.ConnectDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.application.port.from.ControlDeviceDiscoveryPort
import com.mibe.iot.thinker.discovery.application.port.from.GetDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.domain.DiscoveredDevice
import com.welie.blessed.BluetoothCentralManager
import com.welie.blessed.BluetoothCentralManagerCallback
import com.welie.blessed.BluetoothGattService
import com.welie.blessed.BluetoothPeripheral
import com.welie.blessed.BluetoothPeripheralCallback
import com.welie.blessed.ScanResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import javax.annotation.PostConstruct


@Component
class DeviceDiscoveryHandler
@Autowired constructor(
    private val bleDiscoveryResults: BleDiscoveryResults
) : GetDiscoveredDevicePort, ControlDeviceDiscoveryPort, ConnectDiscoveredDevicePort {
    private val log = KotlinLogging.logger {}

    @Value("\${thinker.ble.rssiThreshold:-120}")
    private lateinit var rssiThreshold: String

    @Value("\${thinker.ble.rediscoveryTimeout:30}")
    private lateinit var rediscoveryTimeout: String

    private lateinit var central: BluetoothCentralManager
    private val isActive = AtomicBoolean(false)
    private val discoveryRequestsAmount = AtomicInteger(0)

    @PostConstruct
    private fun initCentralManager() {
        val blePeripheralCallback = object : BluetoothPeripheralCallback() {
            override fun onServicesDiscovered(
                peripheral: BluetoothPeripheral,
                services: MutableList<BluetoothGattService>
            ) {
                val discoveredAt = LocalDateTime.now()
                log.info { "Discovered services for ${peripheral.address}: ${services}" }
                bleDiscoveryResults.devicesWithServices[peripheral.address] =
                    BleDiscoveredDevice(
                        peripheral.toDiscoveredDevice(discoveredAt),
                        services = services.toList()
                    )
                peripheral.cancelConnection()
            }
        }
        val bleCentralCallback = object : BluetoothCentralManagerCallback() {
            override fun onDiscoveredPeripheral(peripheral: BluetoothPeripheral, scanResult: ScanResult) {
                val address = peripheral.address
                log.trace {
                    "discovered device: address=$address " +
                            "name=${peripheral.name} uuids=${peripheral.device?.uuids}"
                }
                bleDiscoveryResults.noticedDevices[address] = Pair(peripheral, LocalDateTime.now())
                bleDiscoveryResults.run {
                    if (isAllowedToConnect(address) &&
                        (!isDiscovered(address) || isDiscoveryDataOutdated(address, rediscoveryTimeout.toInt()))
                    ) {
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
        central.scanForPeripheralsWithServices(allowedUUIDs.toTypedArray())
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
        return bleDiscoveryResults.noticedDevices.values.map { it.first.toDiscoveredDevice(it.second) }.asFlow()
    }

    override suspend fun connectDevice(address: String) {
        bleDiscoveryResults.allowedAddresses += address
    }
}