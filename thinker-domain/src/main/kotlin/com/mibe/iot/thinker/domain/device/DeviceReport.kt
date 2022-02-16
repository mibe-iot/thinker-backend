package com.mibe.iot.thinker.domain.device

import java.time.LocalDateTime

/**
 * Device report
 *
 * @property id device report id
 * @property deviceId id of device related to this report
 * @property reportData content of the report as key-value pairs
 * @property dateTimeCreated date and time when report was created
 * @constructor Create Device report
 */
data class DeviceReport(
    var id: String?,
    val deviceId: String,
    val reportData: Map<String, String>,
    val dateTimeCreated: LocalDateTime
)
