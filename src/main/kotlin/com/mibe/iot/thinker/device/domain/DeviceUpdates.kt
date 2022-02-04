package com.mibe.iot.thinker.device.domain

data class DeviceUpdates(
    val id: String,
    val name: String?,
    val description: String?,
    var mac: String?,
    val actions: Set<DeviceAction>?
)

fun Device.receiveUpdates(deviceUpdates: DeviceUpdates) = Device(
    id,
    deviceUpdates.name ?: name,
    deviceUpdates.description ?: description,
    deviceUpdates.mac ?: mac,
    deviceUpdates.actions ?: actions
)
