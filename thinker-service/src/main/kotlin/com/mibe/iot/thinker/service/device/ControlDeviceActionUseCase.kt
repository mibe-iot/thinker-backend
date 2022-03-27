package com.mibe.iot.thinker.service.device

interface ControlDeviceActionUseCase {
    suspend fun invokeAction(deviceId: String, actionName: String)
}