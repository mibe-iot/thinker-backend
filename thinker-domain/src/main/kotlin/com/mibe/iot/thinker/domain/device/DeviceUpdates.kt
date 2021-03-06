package com.mibe.iot.thinker.domain.device

data class DeviceUpdates(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val actions: Set<DeviceAction>? = null,
    val deviceClass: String? = null,
    val reportTypes: Set<String>? = null
)
