package com.mibe.iot.thinker.device.application.port.to

import com.mibe.iot.thinker.device.domain.DeviceUpdates
import com.mibe.iot.thinker.domain.device.Device
import reactor.core.publisher.Mono

/**
 * Update device use case
 */
interface UpdateDeviceUseCase {
    /**
     * Update device
     *
     * @param deviceUpdates [Mono] of the [DeviceUpdates]
     * @return [Mono] of the updated [Device]
     */
    suspend fun updateDevice(deviceUpdates: DeviceUpdates): Device
}
