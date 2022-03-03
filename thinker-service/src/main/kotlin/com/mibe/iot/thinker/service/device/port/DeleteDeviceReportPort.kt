package com.mibe.iot.thinker.service.device.port

interface DeleteDeviceReportPort {
    suspend fun deleteReport(reportId: String)
}