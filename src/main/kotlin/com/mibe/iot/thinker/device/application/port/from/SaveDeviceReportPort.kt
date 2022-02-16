package com.mibe.iot.thinker.device.application.port.from

import com.mibe.iot.thinker.domain.device.DeviceReport
import reactor.core.publisher.Mono

/**
 * The out port for saving device report
 */
interface SaveDeviceReportPort {
    /**
     * Save report to persistent storage.
     *
     * @param report [Mono] of [DeviceReport]
     * @return [Mono] of the saved [DeviceReport]
     */
    fun saveReport(report: Mono<DeviceReport>): Mono<DeviceReport>
}