package com.mibe.iot.thinker.device.application

import com.mibe.iot.thinker.device.application.port.from.DeleteDevicePort
import com.mibe.iot.thinker.device.application.port.to.DeleteDeviceUseCase
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class DeleteDeviceService(
    private val deleteDevicePort: DeleteDevicePort
) : DeleteDeviceUseCase {

    override fun deleteDevice(id: String): Mono<Void> {
        return deleteDevicePort.deleteDevice(id)
    }
}
