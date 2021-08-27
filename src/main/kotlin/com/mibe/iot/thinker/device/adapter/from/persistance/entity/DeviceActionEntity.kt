package com.mibe.iot.thinker.device.adapter.from.persistance.entity

import com.mibe.iot.thinker.device.domain.DeviceAction

class DeviceActionEntity(
    val name: String,
    val mapping: String,
    val descriptionKey: String
)

fun DeviceAction.toDeviceActionEntity() = DeviceActionEntity(
    name, mapping, descriptionKey
)

fun DeviceActionEntity.toDeviceAction() = DeviceAction(
    name, mapping, descriptionKey
)
