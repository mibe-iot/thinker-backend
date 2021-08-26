package com.mibe.iot.thinker.device.application

import com.mibe.iot.thinker.device.application.port.from.DeleteDevicePort
import com.mibe.iot.thinker.device.application.port.to.DeleteDeviceUseCase
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

/**
 * Delete device service
 *
 * @property deleteDevicePort any implementation of [DeleteDevicePort]
 * @constructor Create Delete device service
 */
@Service
class DeleteDeviceService(
    private val deleteDevicePort: DeleteDevicePort
) : DeleteDeviceUseCase {

    override fun deleteDevice(id: String): Mono<Void> {
        return deleteDevicePort.deleteDevice(id)
    }
}
