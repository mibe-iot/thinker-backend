package com.mibe.iot.thinker.device.application.port.from

import com.mibe.iot.thinker.domain.device.Device
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * The out port for getting device info
 */
interface GetDevicePort {
    fun getDevice(id: String): Mono<Device>
    fun getAllDevices(): Flux<Device>
    fun existsWithId(id: String): Mono<Boolean>
    fun existsWithName(name: String): Mono<Boolean>
}
