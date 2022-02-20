package com.mibe.iot.thinker.device.application

import com.mibe.iot.thinker.device.application.port.from.DeleteDevicePort
import com.mibe.iot.thinker.device.application.port.from.GetDevicePort
import com.mibe.iot.thinker.device.application.port.to.DeleteDeviceUseCase
import com.mibe.iot.thinker.device.application.port.to.exception.DeviceNotFoundException
import org.springframework.stereotype.Service

/**
 * Delete device service
 *
 * @property deleteDevicePort any implementation of [DeleteDevicePort]
 * @constructor Create Delete device service
 */
@Service
class DeleteDeviceService(
    private val deleteDevicePort: DeleteDevicePort,
    private val getDevicePort: GetDevicePort
) : DeleteDeviceUseCase {

    override suspend fun deleteDevice(id: String) {
        if (getDevicePort.existsWithId(id)) {
            deleteDevicePort.deleteDevice(id)
        } else {
            throw DeviceNotFoundException(id)
        }

    }
}
