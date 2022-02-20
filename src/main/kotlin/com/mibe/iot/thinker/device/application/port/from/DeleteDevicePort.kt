package com.mibe.iot.thinker.device.application.port.from

/**
 * Deletes device and all connected to it info from persistent repository
 */
interface DeleteDevicePort {
    suspend fun deleteDevice(id: String)
}
