package com.mibe.iot.thinker.app.discovery.adapter.from.ble

import com.mibe.iot.thinker.constants.PROFILE_DEFAULT
import com.mibe.iot.thinker.constants.PROFILE_PROD
import com.mibe.iot.thinker.service.discovery.port.ConnectDiscoveredDevicePort
import com.mibe.iot.thinker.service.discovery.port.ControlDeviceDiscoveryPort
import com.mibe.iot.thinker.service.discovery.port.GetDiscoveredDevicePort
import com.mibe.iot.thinker.domain.discovery.DeviceConnectionData
import com.mibe.iot.thinker.domain.discovery.DiscoveredDevice
import com.mibe.iot.thinker.domain.device.Device
import com.welie.blessed.BluetoothCentralManager
import com.welie.blessed.BluetoothCentralManagerCallback
import com.welie.blessed.BluetoothPeripheral
import com.welie.blessed.ScanResult
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicBoolean
import javax.annotation.PostConstruct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component


@Component
@Profile(PROFILE_PROD, PROFILE_DEFAULT)
class DeviceDiscoveryHandler
@Autowired constructor(
    private val discoveryDataHolder: DiscoveryDataHolder,
    private val blePeripheralCallback: BlePeripheralCallback
) : GetDiscoveredDevicePort, ControlDeviceDiscoveryPort, ConnectDiscoveredDevicePort {
    private val log = KotlinLogging.logger {}

    private var discoveryStartTime: LocalDateTime = LocalDateTime.now()

    @Value("\${thinker.ble.rssiThreshold:-120}")
    private lateinit var rssiThreshold: String

    private lateinit var central: BluetoothCentralManager
    private val isActive = AtomicBoolean(false)

    @PostConstruct
    private fun initCentralManager() {
        val bleCentralCallback = object : BluetoothCentralManagerCallback() {
            override fun onDiscoveredPeripheral(peripheral: BluetoothPeripheral, scanResult: ScanResult) {

                val discoveredDevice =
                    DiscoveredDevice(address = peripheral.address, discoveredAt = LocalDateTime.now(), peripheral.name)

                log.trace {
                    "Discovered device: address=${discoveredDevice.address} " + "name=${peripheral.name} uuids=${peripheral.device?.uuids}"
                }

                discoveryDataHolder.run {
                    discoveredDevices[discoveredDevice.address] = discoveredDevice
                    if (shouldBeConfigured(discoveredDevice.address)) {
                        central.connectPeripheral(peripheral, blePeripheralCallback)
                    }
                }
            }
        }
        central = BluetoothCentralManager(bleCentralCallback)
        central.setRssiThreshold(rssiThreshold.toInt())
    }

    override suspend fun startDiscovery(connectionData: DeviceConnectionData) {
        discoveryStartTime = LocalDateTime.now()
        discoveryDataHolder.connectionData = connectionData
        isActive.set(true)
        central.scanForPeripherals()
    }

    override fun stopDiscovery() {
        central.stopScan()
        isActive.set(false)
    }

    override fun setConnectableDevices(devicesAndActions: Map<Device, Pair<() -> Unit, () -> Unit>>) {
        discoveryDataHolder.connectableDevices.addAll(devicesAndActions.keys)
        discoveryDataHolder.deviceConfigurationCallbacks += devicesAndActions.map {
            it.key.address to DeviceConfigurationCallbacks(
                it.value.first,
                it.value.second
            )
        }
    }

    override fun addConnectableDevices(
        device: Device,
        onConnectionSuccess: () -> Unit,
        onConnectionFailure: () -> Unit
    ) {
        discoveryDataHolder.apply {
            connectableDevices.add(device)
            deviceConfigurationCallbacks += device.address to DeviceConfigurationCallbacks(
                onConnectionSuccess,
                onConnectionFailure
            )
        }
    }

    override fun isDiscovering(): Boolean {
        return isActive.get()
    }

    override suspend fun getDiscoveredDevices(): Flow<DiscoveredDevice> {
        return discoveryDataHolder.discoveredDevices.values.asFlow()
    }

    override suspend fun getConnectedDeviceByAddress(address: String): DiscoveredDevice? {
        return discoveryDataHolder.discoveredDevices[address]
    }

    override fun removeConnectableDevice(device: Device) {
        discoveryDataHolder.removeConnectableDevice(device)
    }

    override fun getDiscoveryStartedTime(): LocalDateTime {
        return discoveryStartTime
    }
}