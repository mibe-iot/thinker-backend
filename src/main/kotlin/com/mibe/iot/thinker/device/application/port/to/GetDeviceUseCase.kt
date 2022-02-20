package com.mibe.iot.thinker.device.application.port.to

import com.mibe.iot.thinker.domain.device.Device
import kotlinx.coroutines.flow.Flow

/**
 * Get device use case
 */
interface GetDeviceUseCase {
    /**
     * Finds device by id
     *
     * @return [Device] found by id or null
     */
    suspend fun getDevice(id: String): Device?

    /**
     * Get all devices.
     *
     * @return [Flow] of [Device]s
     */
    fun getAllDevices(): Flow<Device>

    suspend fun existsById(deviceId: String): Boolean
}
