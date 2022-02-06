package com.mibe.iot.thinker.discovery.adapter.from.ble

import com.mibe.iot.thinker.discovery.application.port.from.ControlDeviceDiscoveryPort
import com.mibe.iot.thinker.discovery.application.port.from.GetDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.domain.DiscoveredDevice
import com.welie.blessed.BluetoothCentralManager
import com.welie.blessed.BluetoothCentralManagerCallback
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct


@Component
class DeviceDiscoveryHandler
@Autowired constructor(
    private val bleCentralCallback: BluetoothCentralManagerCallback
) : GetDiscoveredDevicePort, ControlDeviceDiscoveryPort {

    private lateinit var central: BluetoothCentralManager

    @PostConstruct
    private fun initCentralManager() {
        central = BluetoothCentralManager(bleCentralCallback)
    }

    override suspend fun startDiscovery() {
        central.scanForPeripherals()
    }

    override fun stopDiscovery() {
        central.stopScan()
    }

    override suspend fun getDiscoveredDevices(): Flow<DiscoveredDevice> {
        TODO("Not yet implemented")
    }
}