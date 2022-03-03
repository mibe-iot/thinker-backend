package com.mibe.iot.thinker.domain.device

import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceAction

data class DeviceUpdates(
    val id: String,
    val name: String?,
    val address: String?,
    val description: String?,
    val actions: Set<DeviceAction>?
)

fun Device.receiveUpdates(deviceUpdates: DeviceUpdates) = Device(
    id = id,
    name = deviceUpdates.name ?: name,
    address = deviceUpdates.address ?: address,
    description = deviceUpdates.description ?: description,
    actions = deviceUpdates.actions ?: actions
)
