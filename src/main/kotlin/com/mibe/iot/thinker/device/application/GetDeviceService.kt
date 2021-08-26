package com.mibe.iot.thinker.device.application

import com.mibe.iot.thinker.device.application.port.from.GetDevicePort
import com.mibe.iot.thinker.device.application.port.to.GetDeviceUseCase
import com.mibe.iot.thinker.device.application.port.to.exception.DeviceNotFoundException
import com.mibe.iot.thinker.device.domain.Device
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class GetDeviceService @Autowired constructor(
    private val getDevicePort: GetDevicePort
) : GetDeviceUseCase {

    override fun getDevice(id: String): Mono<Device> {
        return getDevicePort.getDevice(id).switchIfEmpty(Mono.error(DeviceNotFoundException(id)))
    }
}
