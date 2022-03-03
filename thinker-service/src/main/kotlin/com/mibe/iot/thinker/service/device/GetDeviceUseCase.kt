package com.mibe.iot.thinker.service.device

import com.mibe.iot.thinker.domain.device.Device
import kotlinx.coroutines.flow.Flow

/**
 * Get device use case
 */
interface GetDeviceUseCase {
    /**
     * Finds device by id
     *
     * @return [Device] found by id
     */
    suspend fun getDevice(id: String): Device

    /**
     * Finds device by MAC address
     *
     * @return [Device] found by address
     */
    suspend fun getDeviceByAddress(address: String): Device

    /**
     * Get all devices.
     *
     * @return [Flow] of [Device]s
     */
    fun getAllDevices(): Flow<Device>
}
