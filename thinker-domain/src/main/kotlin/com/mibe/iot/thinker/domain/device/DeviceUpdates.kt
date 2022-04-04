package com.mibe.iot.thinker.domain.device

import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceAction

data class DeviceUpdates(
    val id: String? = null,
    val name: String? = null,
    val address: String? = null,
    val description: String? = null,
    val actions: Set<DeviceAction>? = null,
    val deviceClass: String? = null,
    val reportTypes: Set<String>? = null
)

fun Device.receiveUpdates(deviceUpdates: DeviceUpdates) = Device(
    id = id,
    name = deviceUpdates.name ?: name,
    address = deviceUpdates.address ?: address,
    description = deviceUpdates.description ?: description,
    actions = deviceUpdates.actions ?: actions,
    deviceClass = deviceUpdates.deviceClass ?: deviceClass,
    reportTypes = deviceUpdates.reportTypes ?: reportTypes
)
