package com.mibe.iot.thinker.device.application.port.from

import com.mibe.iot.thinker.domain.device.Device
import reactor.core.publisher.Mono

/**
 * The out port for updating device state
 */
interface UpdateDevicePort {
    fun updateDevice(device: Mono<Device>): Mono<Device>
}
