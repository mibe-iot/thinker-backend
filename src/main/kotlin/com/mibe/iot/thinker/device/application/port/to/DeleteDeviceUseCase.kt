package com.mibe.iot.thinker.device.application.port.to

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
