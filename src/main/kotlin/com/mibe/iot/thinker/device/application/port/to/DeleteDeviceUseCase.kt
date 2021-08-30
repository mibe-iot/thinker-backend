package com.mibe.iot.thinker.device.application.port.to

import reactor.core.publisher.Mono

/**
 * Delete device use case
 */
interface DeleteDeviceUseCase {
    /**
     * Delete device by id
     *
     * @param id id of the device
     * @return [Mono] of void to event the time, when device was deleted
     */
    fun deleteDevice(id: String): Mono<Void>
}
