package com.mibe.iot.thinker.persistence.domain

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
