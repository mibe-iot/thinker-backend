package com.mibe.iot.thinker.device.application

import com.mibe.iot.thinker.device.application.port.from.UpdateDevicePort
import com.mibe.iot.thinker.device.application.port.to.UpdateDeviceUseCase
import com.mibe.iot.thinker.device.domain.Device
import com.mibe.iot.thinker.device.domain.validation.validateDevice
import com.mibe.iot.thinker.validation.application.mapToErrorMonoIfInvalid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UpdateDeviceService @Autowired constructor(
    private val updateDevicePort: UpdateDevicePort
) : UpdateDeviceUseCase {

    override fun updateDevice(device: Mono<Device>): Mono<Device> {
        return device.map {
            mapToErrorMonoIfInvalid(it, validateDevice)
        }.flatMap { updateDevicePort.updateDevice(it) }
    }
}
