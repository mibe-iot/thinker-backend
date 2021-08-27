package com.mibe.iot.thinker.device.domain

data class DeviceUpdates(
    val id: String,
    val name: String?,
    val description: String?,
    val ip: String?
)

fun Device.receiveUpdates(deviceUpdates: DeviceUpdates) = Device(
    id,
    deviceUpdates.name ?: name,
    deviceUpdates.description ?: description,
    deviceUpdates.ip ?: ip
)
