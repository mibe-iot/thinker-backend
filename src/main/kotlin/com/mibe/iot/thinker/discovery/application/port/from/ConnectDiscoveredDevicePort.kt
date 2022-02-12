package com.mibe.iot.thinker.discovery.application.port.from

import com.mibe.iot.thinker.discovery.domain.DeviceConnectionData
import com.mibe.iot.thinker.discovery.domain.DiscoveredDevice

interface ConnectDiscoveredDevicePort {
    suspend fun connectDevice(discoveredDevice: DiscoveredDevice, connectionData: DeviceConnectionData)
}