package com.mibe.iot.thinker.device.application.port.to

import com.mibe.iot.thinker.domain.device.DeviceReport
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface GetDeviceReportUseCase {
    fun getDeviceReport(reportId: String): Mono<DeviceReport>
    fun getDeviceReportsByDeviceId(deviceId: String, page: Int, pageSize: Int): Flux<DeviceReport>
}
