package com.mibe.iot.thinker.device.application.port.from

import reactor.core.publisher.Mono

/**
 * Deletes device and all connected to it info from persistent repository
 */
interface DeleteDevicePort {
    fun deleteDevice(id: String): Mono<Void>
}