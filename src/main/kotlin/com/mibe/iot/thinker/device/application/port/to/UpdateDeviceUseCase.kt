package com.mibe.iot.thinker.device.application.port.to

import com.mibe.iot.thinker.device.domain.Device
import reactor.core.publisher.Mono

/**
 * Update device use case
 */
interface UpdateDeviceUseCase {
    /**
     * Update device
     *
     * @param device [Mono] of the [Device] to update
     * @return [Mono] of the updated [Device]
     */
    fun updateDevice(device: Mono<Device>): Mono<Device>
}
