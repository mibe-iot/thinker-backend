package com.mibe.iot.thinker.device.application.port.to

import com.mibe.iot.thinker.domain.device.Device
import kotlinx.coroutines.flow.Flow
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

    /**
     * Get all devices.
     *
     * @return [Flow] of [Device]s
     */
    fun getAllDevices(): Flow<Device>
}
