package com.mibe.iot.thinker.service.device.port

import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceStatus
import kotlinx.coroutines.flow.Flow

/**
 * The out port for updating device state
 */
interface UpdateDevicePort {
    suspend fun updateDevice(device: Device): Device
    suspend fun updateStatusByIds(deviceIds: Flow<String>, newStatus: DeviceStatus)
}
