package com.mibe.iot.thinker.service.discovery

import com.mibe.iot.thinker.domain.discovery.DiscoveredDevice
import kotlinx.coroutines.flow.Flow

interface GetDiscoveredDeviceUseCase {
    suspend fun getDiscoveredDevices(): Flow<DiscoveredDevice>
}