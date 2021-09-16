package com.mibe.iot.thinker.device.adapter.to.web.dto

import com.mibe.iot.thinker.device.domain.DeviceReport
import java.time.LocalDateTime

data class DeviceReportDto(
    val reportData: Map<String, String>,
)

fun DeviceReportDto.toDeviceReport(deviceId: String) = DeviceReport(
    id = null,
    deviceId = deviceId,
    reportData = HashMap(reportData),
    LocalDateTime.now()
)
