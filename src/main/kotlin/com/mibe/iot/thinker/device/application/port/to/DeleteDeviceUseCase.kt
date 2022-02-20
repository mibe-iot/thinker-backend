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
     */
    suspend fun deleteDevice(id: String)
}
