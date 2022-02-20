package com.mibe.iot.thinker.device.application.port.to

import com.mibe.iot.thinker.domain.device.DeviceReport
import reactor.core.publisher.Flux

interface GetDeviceReportUseCase {
    /**
     * Gets device's report by report id and device id
     *
     * @throws [com.mibe.iot.thinker.device.application.port.to.exception.DeviceReportNotFoundException]
     * on report not found
     */
    suspend fun getDeviceReport(reportId: String, deviceId: String): DeviceReport


    fun getDeviceReportsByDeviceId(deviceId: String, page: Int, pageSize: Int): Flux<DeviceReport>
}
