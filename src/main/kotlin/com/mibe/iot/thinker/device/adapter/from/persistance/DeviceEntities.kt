package com.mibe.iot.thinker.device.adapter.from.persistance

import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceAction
import com.mibe.iot.thinker.domain.device.DeviceReport
import com.mibe.iot.thinker.persistence.domain.DeviceActionEntity
import com.mibe.iot.thinker.persistence.domain.DeviceEntity
import com.mibe.iot.thinker.persistence.domain.DeviceReportEntity

fun DeviceAction.toDeviceActionEntity() = DeviceActionEntity(
    name, mapping, descriptionKey
)

fun DeviceActionEntity.toDeviceAction() = DeviceAction(
    name, mapping, descriptionKey
)

fun Device.toDeviceEntity() = DeviceEntity(
    id = id,
    name = name,
    address = address,
    description = description,
    actions = actions.map { it.toDeviceActionEntity() }.toSet()
)

fun DeviceEntity.toDevice() = Device(
    id = id,
    name = name,
    address = address,
    description = description,
    actions = actions.map { it.toDeviceAction() }.toSet()
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
