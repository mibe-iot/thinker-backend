package com.mibe.iot.thinker.discovery.application

import com.mibe.iot.thinker.discovery.application.exception.DeviceNotFoundException
import com.mibe.iot.thinker.discovery.application.port.from.GetDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.application.port.from.SaveDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.application.port.to.ConnectDiscoveredDeviceUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConnectDiscoveredDeviceService
@Autowired constructor(
    private val saveDiscoveredDevicePort: SaveDiscoveredDevicePort,
    private val getDiscoveredDevicePort: GetDiscoveredDevicePort
) : ConnectDiscoveredDeviceUseCase {

    override suspend fun connectDeviceByAddress(address: String) {
        val discoveredDevice = getDiscoveredDevicePort.getConnectedDeviceByAddress(address)
            ?: throw DeviceNotFoundException("Device with address=$address wasn't found")
        val deviceName = randomString(DEVICE_NAME_LENGTH) //TODO Not random, but sequential
        val deviceId = saveDiscoveredDevicePort.saveDiscoveredDevice(discoveredDevice, deviceName)
    }
}