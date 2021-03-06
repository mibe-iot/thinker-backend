package com.mibe.iot.thinker.service.device.port

import com.mibe.iot.thinker.domain.device.DeviceReport

/**
 * The out port for saving device report
 */
interface SaveDeviceReportPort {
    /**
     * Save report to persistent storage.
     *
     * @param report [DeviceReport]
     * @return saved [DeviceReport]
     */
    suspend fun saveReport(report: DeviceReport): DeviceReport
}