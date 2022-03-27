package com.mibe.iot.thinker.app.device.to.mqtt

import com.mibe.iot.thinker.domain.device.DeviceAction

data class DeviceActionsData(
    val deviceName: String,
    val actions: List<DeviceAction>
)
