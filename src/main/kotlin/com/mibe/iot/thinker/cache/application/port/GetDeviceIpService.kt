package com.mibe.iot.thinker.cache.application.port

import com.mibe.iot.thinker.cache.application.port.from.ReadDeviceIpPort
import com.mibe.iot.thinker.cache.application.port.from.SaveDeviceIpPort
import com.mibe.iot.thinker.cache.application.port.to.GetDeviceIpUseCase
import com.mibe.iot.thinker.device.domain.Device
import com.mibe.iot.thinker.web.domain.IpAddress
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

class GetDeviceIpService @Autowired constructor(
    private val readDeviceIpPort: ReadDeviceIpPort,
    private val saveDeviceIpPort: SaveDeviceIpPort
) : GetDeviceIpUseCase {
    override fun getDeviceIp(device: Mono<Device>): Mono<IpAddress> {
        return device.flatMap {
            readDeviceIpPort.readDeviceIp(it.id!!).toMono()
        }.switchIfEmpty(device.flatMap { resolveIpFromMac(it.mac).toMono() })
    }

    private fun resolveIpFromMac(mac: String?): IpAddress {
        TODO()
    }
}
