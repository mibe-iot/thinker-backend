package com.mibe.iot.thinker.discovery.application.port.from

import com.mibe.iot.thinker.discovery.domain.DeviceConnectionData
import com.mibe.iot.thinker.discovery.domain.DiscoveredDevice

interface ConnectDiscoveredDevicePort {
    suspend fun connectDevice(connectionData: DeviceConnectionData)
    suspend fun reconnectDevice(connectionData: DeviceConnectionData)
}