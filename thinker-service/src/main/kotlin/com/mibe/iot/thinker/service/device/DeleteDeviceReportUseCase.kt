package com.mibe.iot.thinker.service.device

interface DeleteDeviceReportUseCase {
    suspend fun deleteReport(reportId: String, deviceId: String)
}