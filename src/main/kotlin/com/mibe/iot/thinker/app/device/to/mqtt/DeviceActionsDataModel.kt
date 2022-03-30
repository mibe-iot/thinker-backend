package com.mibe.iot.thinker.app.device.to.mqtt

import com.mibe.iot.thinker.domain.device.DeviceAction

data class DeviceActionsDataModel(
    val deviceId: String,
    val deviceClass: String,
    val actions: List<DeviceActionModel>
)
