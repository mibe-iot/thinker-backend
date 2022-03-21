package com.mibe.iot.thinker.service.discovery.port

import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceStatus
import com.mibe.iot.thinker.domain.discovery.DiscoveredDevice

interface SaveDiscoveredDevicePort {
    suspend fun saveDiscoveredDevice(discoveredDevice: DiscoveredDevice): Device
    suspend fun updateDeviceStatus(deviceId: String, deviceStatus: DeviceStatus)
}