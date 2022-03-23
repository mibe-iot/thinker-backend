package com.mibe.iot.thinker.service.device

import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceUpdates

/**
 * Update device use case
 */
interface UpdateDeviceUseCase {
    /**
     * Update device
     *
     * @param deviceUpdates [DeviceUpdates]
     * @return updated [Device]
     */
    suspend fun updateDevice(deviceUpdates: DeviceUpdates): Device
}
