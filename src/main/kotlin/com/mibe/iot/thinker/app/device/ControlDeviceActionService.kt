package com.mibe.iot.thinker.app.device

import com.mibe.iot.thinker.service.device.ControlDeviceActionUseCase
import com.mibe.iot.thinker.service.device.GetDeviceUseCase
import com.mibe.iot.thinker.service.device.exception.DeviceActionNotFoundException
import com.mibe.iot.thinker.service.device.port.ControlDeviceActionPort
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import org.springframework.stereotype.Service

@Service
class ControlDeviceActionService(
    private val getDeviceUseCase: GetDeviceUseCase,
    private val controlDeviceActionPort: ControlDeviceActionPort
) : ControlDeviceActionUseCase {

    override suspend fun invokeAction(deviceId: String, actionName: String) {
        val deviceAction = (getDeviceUseCase.getDeviceActions(deviceId).filter { it.name == actionName }.firstOrNull()
            ?: throw DeviceActionNotFoundException(deviceId, actionName))
        controlDeviceActionPort.activateAction(deviceId, deviceAction)
    }

}