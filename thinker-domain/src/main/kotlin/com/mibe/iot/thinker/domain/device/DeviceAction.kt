package com.mibe.iot.thinker.domain.device

data class DeviceAction(
    val name: String,
    val deviceName: String,
    var descriptionKey: String? = ""
)