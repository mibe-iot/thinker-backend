package com.mibe.iot.thinker.service.device

import com.mibe.iot.thinker.domain.device.DeviceReport

/**
 * SaveDeviceReportUseCase is an operation of saving device report
 */
interface SaveDeviceReportUseCase {

    suspend fun saveReport(report: DeviceReport): DeviceReport
}
