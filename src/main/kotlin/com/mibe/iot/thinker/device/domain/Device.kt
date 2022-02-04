package com.mibe.iot.thinker.device.domain

/**
 * Device data class represents a single device in IoT network (bit)
 */
data class Device(
    var id: String?,
    val name: String,
    val description: String,
    val mac: String?,
    var actions: Set<DeviceAction> = setOf()
)
