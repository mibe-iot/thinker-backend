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
     * @param report [Mono] of [DeviceReport]
     * @return [Mono] of the saved [DeviceReport]
     */
    fun saveReport(report: Mono<DeviceReport>): Mono<DeviceReport>
}
