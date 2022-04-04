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
    suspend fun updateDevicePartially(deviceId: String, deviceUpdates: DeviceUpdates)

    suspend fun updateDeviceAdditionalData(deviceAdditionalData: DeviceUpdates)
}
