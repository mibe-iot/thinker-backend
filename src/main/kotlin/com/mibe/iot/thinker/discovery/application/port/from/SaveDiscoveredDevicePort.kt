package com.mibe.iot.thinker.discovery.application.port.from

import com.mibe.iot.thinker.discovery.domain.DiscoveredDevice

interface SaveDiscoveredDevicePort {
    suspend fun saveDiscoveredDevice(discoveredDevice: DiscoveredDevice, name: String): String
}