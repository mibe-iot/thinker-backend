package com.mibe.iot.thinker.device.application.port.from

import com.mibe.iot.thinker.domain.device.DeviceReport
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface GetDeviceReportPort {

    fun getById(reportId: String): Mono<DeviceReport>
    fun getByDeviceId(deviceId: String, pageable: Pageable): Flux<DeviceReport>
}
