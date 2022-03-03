package com.mibe.iot.thinker.app.device

import com.mibe.iot.thinker.service.device.port.DeleteDevicePort
import com.mibe.iot.thinker.service.device.DeleteDeviceUseCase
import com.mibe.iot.thinker.service.device.exception.DeviceNotFoundException
import com.mibe.iot.thinker.service.device.port.GetDevicePort
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
