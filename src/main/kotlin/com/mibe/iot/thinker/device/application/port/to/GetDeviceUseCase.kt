package com.mibe.iot.thinker.device.application.port.to

import com.mibe.iot.thinker.device.domain.Device
import reactor.core.publisher.Mono

/**
 * Get device use case
 */
interface GetDeviceUseCase {
    /**
     * Finds device by id
     *
     * @return [Mono] of the [Device] found by id
     * @throws [com.mibe.iot.thinker.device.application.port.to.exception.DeviceNotFoundException] when no Device found
     */
    fun getDevice(id: String): Mono<Device>
}
