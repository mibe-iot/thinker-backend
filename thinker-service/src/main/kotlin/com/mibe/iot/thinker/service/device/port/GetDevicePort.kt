package com.mibe.iot.thinker.service.device.port

import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceAction
import kotlinx.coroutines.flow.Flow
import reactor.core.publisher.Mono

/**
 * The out port for getting device info
 */
interface GetDevicePort {
    suspend fun getDevice(id: String): Device?
    suspend fun getDeviceActions(id: String): Flow<DeviceAction>?
    suspend fun getDeviceByAddress(address: String): Device?
    fun getAllDevices(): Flow<Device>
    suspend fun existsWithId(id: String): Boolean
    fun existsWithName(name: String): Mono<Boolean>
    suspend fun getAllWithDifferentHash(configurationHash: Int): Flow<Device>
}
