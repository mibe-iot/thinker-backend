package com.mibe.iot.thinker.app.discovery.from.ble

import com.mibe.iot.thinker.PROFILE_DEFAULT
import com.mibe.iot.thinker.PROFILE_PROD
import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.discovery.DeviceConfigurationCallbacks
import com.mibe.iot.thinker.domain.discovery.DeviceConnectionData
import com.mibe.iot.thinker.domain.discovery.DiscoveredDevice
import com.mibe.iot.thinker.service.discovery.port.ConnectDiscoveredDevicePort
import com.mibe.iot.thinker.service.discovery.port.ControlDeviceDiscoveryPort
import com.mibe.iot.thinker.service.discovery.port.GetDiscoveredDevicePort
import com.welie.blessed.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicBoolean
import javax.annotation.PostConstruct

@Component
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
                val isKnownDevice = peripheral.device?.uuids?.any { allowedUUIDs.contains(it) } ?: false
                val discoveredDevice =
                    DiscoveredDevice(
                        address = peripheral.address,
                        discoveredAt = LocalDateTime.now(),
                        name = peripheral.name,
                        rssi = scanResult.rssi,
                        knownDevice = isKnownDevice
                    )

                log.trace {
                    "Discovered device: address=${discoveredDevice.address} " +
                            "name=${peripheral.name} uuids=${peripheral.device?.uuids}"
                }

                discoveryDataHolder.run {
                    discoveredDevices[discoveredDevice.address] = discoveredDevice
                    if (shouldBeConfigured(discoveredDevice.address)) {
                        central.connectPeripheral(peripheral, blePeripheralCallback)
                    }
                }

            }

            override fun onConnectionFailed(peripheral: BluetoothPeripheral, status: BluetoothCommandStatus) {
                log.warn{ "Device connection failed. Removing it from connectable list" }
                discoveryDataHolder.deviceConfigurationCallbacks[peripheral.address]?.let { it.onConfigurationFailed() }
            }
        }
        central = BluetoothCentralManager(bleCentralCallback)
        central.setRssiThreshold(rssiThreshold.toInt())
    }

    override suspend fun startDiscovery() {
        requireNotNull(discoveryDataHolder.connectionData)
        discoveryStartTime = LocalDateTime.now()
        isActive.set(true)
        central.scanForPeripherals()
    }

    override suspend fun updateConnectionData(connectionData: DeviceConnectionData) {
        discoveryDataHolder.connectionData = connectionData
    }

    override fun stopDiscovery() {
        central.stopScan()
        isActive.set(false)
    }

    override fun setConnectableDevices(devicesAndActions: Map<Device, DeviceConfigurationCallbacks>) {
        discoveryDataHolder.connectableDevices.addAll(devicesAndActions.keys)
        log.info { "Connectable devices: ${discoveryDataHolder.connectableDevices}" }
        discoveryDataHolder.deviceConfigurationCallbacks += devicesAndActions.map { it.key.address to it.value }
    }

    override fun addConnectableDevices(
        device: Device,
        deviceConfigurationCallbacks: DeviceConfigurationCallbacks
    ) {
        discoveryDataHolder.apply {
            connectableDevices.add(device)
            this.deviceConfigurationCallbacks += device.address to deviceConfigurationCallbacks
        }
    }

    override fun isDiscovering() = isActive.get()

    override suspend fun getDiscoveredDevices() = discoveryDataHolder.discoveredDevices.values.asFlow()

    override suspend fun getConnectedDeviceByAddress(address: String) = discoveryDataHolder.discoveredDevices[address]

    override suspend fun isDeviceWaitingConfiguration(address: String) = discoveryDataHolder.shouldBeConfigured(address)

    override fun removeConnectableDevice(device: Device) = discoveryDataHolder.removeConnectableDevice(device)

    override fun getDiscoveryStartedTime() = discoveryStartTime
}