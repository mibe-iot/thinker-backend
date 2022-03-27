package com.mibe.iot.thinker.app.device.to.mqtt

import com.mibe.iot.thinker.domain.device.DeviceAction

data class DeviceActionsDataModel(
    val deviceName: String,
    val actions: List<DeviceActionModel>
)
