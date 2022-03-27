package com.mibe.iot.thinker.service.device.port

import com.mibe.iot.thinker.domain.device.DeviceAction

interface ControlDeviceActionPort {
    fun activateAction(deviceName: String, action: DeviceAction)
}