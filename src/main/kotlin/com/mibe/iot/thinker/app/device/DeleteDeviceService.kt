package com.mibe.iot.thinker.app.device

import com.mibe.iot.thinker.domain.device.DeviceAction
import com.mibe.iot.thinker.service.device.DeleteDeviceUseCase
import com.mibe.iot.thinker.service.device.exception.DeviceNotFoundException
import com.mibe.iot.thinker.service.device.port.ControlDeviceActionPort
import com.mibe.iot.thinker.service.device.port.DeleteDevicePort
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
    private val getDevicePort: GetDevicePort,
    private val controlDeviceActionPort: ControlDeviceActionPort
) : DeleteDeviceUseCase {

    override suspend fun deleteDevice(id: String) {
        if (getDevicePort.existsWithId(id)) {
            controlDeviceActionPort.activateActionReliably(id, DeviceAction.delete)
            deleteDevicePort.deleteDevice(id)
        } else {
            throw DeviceNotFoundException(id)
        }

    }
}
