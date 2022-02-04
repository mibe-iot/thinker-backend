package com.mibe.iot.thinker.cache.application.port.to

import com.mibe.iot.thinker.device.domain.Device
import com.mibe.iot.thinker.web.domain.IpAddress
import reactor.core.publisher.Mono

interface GetDeviceIpUseCase {
    fun getDeviceIp(device: Mono<Device>): Mono<IpAddress>
}