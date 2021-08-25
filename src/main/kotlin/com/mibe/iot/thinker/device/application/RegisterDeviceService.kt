package com.mibe.iot.thinker.device.application

import com.mibe.iot.thinker.device.application.port.from.UpdateDevicePort
import com.mibe.iot.thinker.device.application.port.to.RegisterDeviceUseCase
import com.mibe.iot.thinker.device.domain.Device
import com.mibe.iot.thinker.device.domain.validation.validateNewDevice
import com.mibe.iot.thinker.validation.mapToErrorMonoIfInvalid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class RegisterDeviceService
@Autowired constructor(
    private val updateDevicePort: UpdateDevicePort
) : RegisterDeviceUseCase {

    /**
     * Validates device and passes it to UpdateDevicePort.
     * @return Device with updated info such given id
     */
    override fun registerDevice(device: Mono<Device>): Mono<Device> {
        return device.map {
            mapToErrorMonoIfInvalid(it, validateNewDevice)
        }.flatMap { updateDevicePort.updateDevice(it) }
    }
}
