package com.mibe.iot.thinker.device.domain

/**
 * Device represents a single device in IoT network (bit)
 */
data class Device(
    var id: String?,
    val name: String,
    val address: String,
    val description: String = "",
    var actions: Set<DeviceAction> = emptySet()
)
