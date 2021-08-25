package com.mibe.iot.thinker.device.application.port.to

import com.mibe.iot.thinker.device.domain.Device
import reactor.core.publisher.Mono

/**
 * RegisterDeviceUseCase is an operation of registering device into the IoT network
 */
interface RegisterDeviceUseCase {
    fun registerDevice(device: Mono<Device>): Mono<Device>
}
