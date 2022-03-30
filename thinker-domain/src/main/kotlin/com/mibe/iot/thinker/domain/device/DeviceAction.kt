package com.mibe.iot.thinker.domain.device

data class DeviceAction(
    val name: String,
    var displayName: String = "",
    var description: String = ""
)