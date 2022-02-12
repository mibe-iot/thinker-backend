package com.mibe.iot.thinker.discovery.application.port.from

import com.mibe.iot.thinker.discovery.domain.DiscoveredDevice
import kotlinx.coroutines.flow.Flow

interface GetDiscoveredDevicePort {
    suspend fun getDiscoveredDevices(): Flow<DiscoveredDevice>
    suspend fun getConnectedDeviceByAddress(address: String): DiscoveredDevice?
}