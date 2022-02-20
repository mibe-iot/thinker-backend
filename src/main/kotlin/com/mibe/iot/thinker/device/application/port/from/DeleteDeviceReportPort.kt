package com.mibe.iot.thinker.device.application.port.from

interface DeleteDeviceReportPort {
    suspend fun deleteReport(reportId: String)
}