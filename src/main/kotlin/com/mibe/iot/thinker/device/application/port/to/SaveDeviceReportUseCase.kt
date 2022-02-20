package com.mibe.iot.thinker.device.application.port.to

import com.mibe.iot.thinker.domain.device.DeviceReport
import reactor.core.publisher.Mono

/**
 * SaveDeviceReportUseCase is an operation of saving device report
 */
interface SaveDeviceReportUseCase {
    /**
     * Save report to persistent storage.
     *
     * @param report [DeviceReport]
     * @return saved [DeviceReport]
     */
    suspend fun saveReport(report: DeviceReport): DeviceReport
}
