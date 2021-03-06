package com.mibe.iot.thinker.service.device

import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceAction
import com.mibe.iot.thinker.domain.device.DeviceWithReport
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

    suspend fun getDeviceActions(id: String): Flow<DeviceAction>

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
    suspend fun getAllActiveDevices(): Flow<Device>

    suspend fun getAllDevicesWithLatestReports(): Flow<DeviceWithReport>
}
