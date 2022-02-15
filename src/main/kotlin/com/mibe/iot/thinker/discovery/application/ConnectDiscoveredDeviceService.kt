package com.mibe.iot.thinker.discovery.application

import com.mibe.iot.thinker.discovery.application.exception.DeviceNotFoundException
import com.mibe.iot.thinker.discovery.application.port.from.ConnectDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.application.port.from.GetDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.application.port.from.GetSavedDevicePort
import com.mibe.iot.thinker.discovery.application.port.from.SaveDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.application.port.to.ConnectDiscoveredDeviceUseCase
import com.mibe.iot.thinker.discovery.domain.DeviceConnectionData
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConnectDiscoveredDeviceService
@Autowired constructor(
    private val saveDiscoveredDevicePort: SaveDiscoveredDevicePort,
    private val getDiscoveredDevicePort: GetDiscoveredDevicePort,
    private val getSavedDevicePort: GetSavedDevicePort,
    private val connectDiscoveredDevicePort: ConnectDiscoveredDevicePort
) : ConnectDiscoveredDeviceUseCase {
    private val log = KotlinLogging.logger {}

    override suspend fun connectDeviceByAddress(address: String) {
        val discoveredDevice = getDiscoveredDevicePort.getConnectedDeviceByAddress(address)
            ?: throw DeviceNotFoundException("Device with address=$address wasn't found")
        val deviceName = randomString(DEVICE_NAME_LENGTH) //TODO Not random, but sequential
        val connectionData = DeviceConnectionData(
            address = address,
            deviceName = discoveredDevice.name,
            ssid = "Get-6490C8",
            password = "ewmhjmztvd"
        )

        if(getSavedDevicePort.existsByAddress(address)) {
            val deviceFromPersistence = getSavedDevicePort.getDeviceByAddress(address)
            connectionData.deviceName = deviceFromPersistence.name
            reconnectDeviceByAddress(connectionData)
        } else {
            connectDiscoveredDevicePort.connectDevice(connectionData)
            val deviceId = saveDiscoveredDevicePort.saveDiscoveredDevice(discoveredDevice, deviceName)
            log.info { "Device saved with name=$deviceName and id=$deviceId" }
        }

    }

    private suspend fun reconnectDeviceByAddress(connectionData: DeviceConnectionData) {
        connectDiscoveredDevicePort.reconnectDevice(connectionData)
    }
}