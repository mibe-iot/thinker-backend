package com.mibe.iot.thinker.service.device.port

import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceAction
import com.mibe.iot.thinker.domain.device.DeviceStatus
import com.mibe.iot.thinker.domain.device.DeviceUpdates
import kotlinx.coroutines.flow.Flow

/**
 * The out port for updating device state
 */
interface UpdateDevicePort {
    suspend fun updateDevice(device: Device): Device
    suspend fun updateDevicePartially(deviceId: String, updateData: Map<*, *>)
    suspend fun updateStatusByIds(deviceIds: Flow<String>, newStatus: DeviceStatus)
    suspend fun updateAdditionalData(deviceAdditionalData: DeviceUpdates)
}
