package com.mibe.iot.thinker.device.adapter.from.persistance.entity

import com.mibe.iot.thinker.device.domain.Device
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("devices")
class DeviceEntity(
    @Id
    var id: String?,
    val name: String,
    val description: String,
    val ip: String?,
    val actions: Set<DeviceActionEntity>
)

fun Device.toDeviceEntity() = DeviceEntity(
    id,
    name,
    description,
    mac,
    actions.map { it.toDeviceActionEntity() }.toSet()
)

fun DeviceEntity.toDevice() = Device(
    id,
    name,
    description,
    ip,
    actions.map { it.toDeviceAction() }.toSet()
)
