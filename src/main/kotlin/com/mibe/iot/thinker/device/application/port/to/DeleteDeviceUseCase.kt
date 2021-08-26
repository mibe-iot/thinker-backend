package com.mibe.iot.thinker.device.application.port.to

import reactor.core.publisher.Mono

interface DeleteDeviceUseCase {
    fun deleteDevice(id: String): Mono<Void>
}
