package com.mibe.iot.thinker.service.discovery.port

import com.mibe.iot.thinker.domain.discovery.DiscoveredDevice
import kotlinx.coroutines.flow.Flow

interface GetDiscoveredDevicePort {
    suspend fun getDiscoveredDevices(): Flow<DiscoveredDevice>
    suspend fun getConnectedDeviceByAddress(address: String): DiscoveredDevice?
}