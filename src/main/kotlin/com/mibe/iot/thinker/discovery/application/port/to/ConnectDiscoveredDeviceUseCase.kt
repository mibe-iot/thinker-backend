package com.mibe.iot.thinker.discovery.application.port.to

import com.mibe.iot.thinker.domain.device.Device
import kotlinx.coroutines.flow.Flow

interface ConnectDiscoveredDeviceUseCase {
    suspend fun connectDeviceByAddress(address: String)
    suspend fun setConnectableDevices(devices: Flow<Device>)
}