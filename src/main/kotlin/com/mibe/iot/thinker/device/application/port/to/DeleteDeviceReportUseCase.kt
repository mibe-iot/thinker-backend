package com.mibe.iot.thinker.device.application.port.to

interface DeleteDeviceReportUseCase {
    suspend fun deleteReport(reportId: String, deviceId: String)
}