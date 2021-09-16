package com.mibe.iot.thinker.device.adapter.from.persistance.entity

import com.mibe.iot.thinker.device.domain.DeviceReport
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("deviceReports")
class DeviceReportEntity(
    @Id
    var id: String?,
    val deviceId: String,
    val reportData: Map<String, String>,
    val dateTimeCreated: LocalDateTime
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
