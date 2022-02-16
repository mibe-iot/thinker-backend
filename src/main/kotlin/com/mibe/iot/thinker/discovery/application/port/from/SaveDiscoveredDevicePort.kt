package com.mibe.iot.thinker.discovery.application.port.from

import com.mibe.iot.thinker.discovery.domain.DiscoveredDevice
import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceStatus

interface SaveDiscoveredDevicePort {
    suspend fun saveDiscoveredDevice(discoveredDevice: DiscoveredDevice): Device
    suspend fun updateDeviceStatus(deviceId: String, deviceStatus: DeviceStatus)
}