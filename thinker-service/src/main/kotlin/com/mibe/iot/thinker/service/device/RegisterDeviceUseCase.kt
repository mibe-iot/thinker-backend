package com.mibe.iot.thinker.service.device

import com.mibe.iot.thinker.domain.device.Device
import reactor.core.publisher.Mono

/**
 * RegisterDeviceUseCase is an operation of registering device into the IoT network
 */
interface RegisterDeviceUseCase {
    /**
     * Will register new device to persistent storage. Similar to save operation, but register operation should not let to
     * save same device twice.
     *
     * @param deviceMono [Mono] of the [Device] to save
     * @return [Mono] of the registered [Device]
     */
    fun registerDevice(deviceMono: Mono<Device>): Mono<Device>
}
