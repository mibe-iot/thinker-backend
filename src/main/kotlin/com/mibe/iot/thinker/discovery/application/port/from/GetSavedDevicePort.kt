package com.mibe.iot.thinker.discovery.application.port.from

import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceStatus
import kotlinx.coroutines.flow.Flow

interface GetSavedDevicePort {
    suspend fun getDeviceByAddress(address: String): Device
    suspend fun existsByAddress(address: String): Boolean
    suspend fun getByStatus(status: DeviceStatus): Flow<Device>
}