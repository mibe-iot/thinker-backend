package com.mibe.iot.thinker.persistence.entity

import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("deviceReports")
class DeviceReportEntity(
    @Id
    var id: String?,
    val deviceId: String,
    val reportData: Map<String, String>,
    val reportType: String,
    val dateTimeCreated: LocalDateTime?
)
