package com.mibe.iot.thinker.device.application.port.to

import com.mibe.iot.thinker.device.domain.Device
import reactor.core.publisher.Mono

interface UpdateDeviceUseCase {
    fun updateDevice(device: Mono<Device>): Mono<Device>
}
