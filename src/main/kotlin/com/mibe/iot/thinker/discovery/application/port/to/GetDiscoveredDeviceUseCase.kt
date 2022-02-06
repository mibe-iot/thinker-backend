package com.mibe.iot.thinker.discovery.application.port.to

import com.mibe.iot.thinker.discovery.domain.DiscoveredDevice
import kotlinx.coroutines.flow.Flow

interface GetDiscoveredDeviceUseCase {
    suspend fun getDiscoveredDevices(): Flow<DiscoveredDevice>
}