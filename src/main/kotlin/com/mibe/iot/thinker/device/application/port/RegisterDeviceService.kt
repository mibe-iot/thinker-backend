package com.mibe.iot.thinker.device.application.port

import com.mibe.iot.thinker.device.application.port.from.UpdateDevicePort
import com.mibe.iot.thinker.device.application.port.to.RegisterDeviceUseCase
import com.mibe.iot.thinker.device.domain.Device
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class RegisterDeviceService
@Autowired constructor(
    private val updateDevicePort: UpdateDevicePort
) : RegisterDeviceUseCase {

    override fun registerDevice(device: Mono<Device>): Mono<Device> {
        // device.id should be null TODO
        return updateDevicePort.updateDevice(device)
    }
}
