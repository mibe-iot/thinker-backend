package com.mibe.iot.thinker.service.device

import com.mibe.iot.thinker.domain.device.DeviceReport
import kotlinx.coroutines.flow.Flow

interface GetDeviceReportUseCase {
    /**
     * Gets device's report by report id and device id
     *
     * @throws [com.mibe.iot.thinker.device.application.port.to.exception.DeviceReportNotFoundException]
     * on report not found
     */
    suspend fun getDeviceReport(reportId: String, deviceId: String): DeviceReport

    /**
     * Gets several device reports.
     */
    suspend fun getDeviceReportsByDeviceId(deviceId: String, page: Int, pageSize: Int): Flow<DeviceReport>

    suspend fun getReportsCountByDeviceId(deviceId: String): Long
}
