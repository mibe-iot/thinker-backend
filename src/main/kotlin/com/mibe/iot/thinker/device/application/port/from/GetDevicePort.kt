package com.mibe.iot.thinker.device.application.port.from

import com.mibe.iot.thinker.domain.device.Device
import kotlinx.coroutines.flow.Flow
import reactor.core.publisher.Mono

/**
 * The out port for getting device info
 */
interface GetDevicePort {
    suspend fun getDevice(id: String): Device?
    fun getAllDevices(): Flow<Device>
    fun existsWithId(id: String): Mono<Boolean>
    fun existsWithName(name: String): Mono<Boolean>
}
