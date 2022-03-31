package com.mibe.iot.thinker.app.device.to.mqtt

data class DeviceActionsDataModel(
    val deviceId: String,
    val deviceClass: String,
    val actions: List<DeviceActionModel>,
    val reportTypes: Set<String>
)
