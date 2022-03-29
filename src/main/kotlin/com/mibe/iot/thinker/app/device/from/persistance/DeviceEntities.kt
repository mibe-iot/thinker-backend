package com.mibe.iot.thinker.app.device.from.persistance

import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceAction
import com.mibe.iot.thinker.domain.device.DeviceReport
import com.mibe.iot.thinker.persistence.entity.DeviceActionEntity
import com.mibe.iot.thinker.persistence.entity.DeviceEntity
import com.mibe.iot.thinker.persistence.entity.DeviceReportEntity

fun DeviceAction.toDeviceActionEntity() = DeviceActionEntity(
    name = name,
    displayName = displayName,
    description = description
)

fun DeviceActionEntity.toDeviceAction() = DeviceAction(
    name = name,
    displayName = displayName,
    description = description
)

fun Device.toDeviceEntity() = DeviceEntity(
    id = id,
    name = name,
    address = address,
    description = description,
    actions = actions.map { it.toDeviceActionEntity() }.toSet(),
    status = status,
    connectType = connectType,
    configurationHash = configurationHash,
    deviceClass = deviceClass
)

fun DeviceEntity.toDevice() = Device(
    id = id,
    name = name ?: "",
    address = address,
    description = description,
    actions = actions.map { it.toDeviceAction() }.toSet(),
    status = status,
    connectType = connectType,
    configurationHash = configurationHash,
    deviceClass = deviceClass
)

fun DeviceReport.toDeviceReportEntity() = DeviceReportEntity(
    id = id,
    deviceId = deviceId,
    reportData = HashMap(reportData),
    dateTimeCreated = dateTimeCreated
)

fun DeviceReportEntity.toDeviceReport() = DeviceReport(
    id = id,
    deviceId = deviceId,
    reportData = HashMap(reportData),
    dateTimeCreated = dateTimeCreated
)
