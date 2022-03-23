package com.mibe.iot.thinker.service.device.port

/**
 * Deletes device and all connected to it info from persistent repository
 */
interface DeleteDevicePort {
    suspend fun deleteDevice(id: String)
}
