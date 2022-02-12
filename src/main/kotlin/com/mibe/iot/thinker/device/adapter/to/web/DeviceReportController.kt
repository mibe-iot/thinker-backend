package com.mibe.iot.thinker.device.adapter.to.web

import com.mibe.iot.thinker.device.adapter.to.web.dto.DeviceReportDto
import com.mibe.iot.thinker.device.adapter.to.web.dto.toDeviceReport
import com.mibe.iot.thinker.device.application.port.to.GetDeviceReportUseCase
import com.mibe.iot.thinker.device.application.port.to.SaveDeviceReportUseCase
import com.mibe.iot.thinker.device.domain.DeviceReport
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("/api/devices/{deviceId}/reports")
internal class DeviceReportController @Autowired constructor(
    private val saveDeviceReportUseCase: SaveDeviceReportUseCase,
    private val getDeviceReportUseCase: GetDeviceReportUseCase
) {

    @PostMapping("")
    suspend fun saveReport(
        @PathVariable deviceId: String,
        @RequestBody deviceReportDto: Mono<DeviceReportDto>,
        exchange: ServerWebExchange
    ): DeviceReport {
        return deviceReportDto.flatMap { reportDto ->
            saveDeviceReportUseCase.saveReport(reportDto.toDeviceReport(deviceId).toMono())
        }.awaitFirst()
    }

    @GetMapping("")
    fun getReportsByDeviceId(
        @PathVariable deviceId: String,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") pageSize: Int,
        exchange: ServerWebExchange
    ) = getDeviceReportUseCase.getDeviceReportsByDeviceId(deviceId, page, pageSize).asFlow()

    @GetMapping("/{reportId}")
    fun getReport(
        @PathVariable reportId: String,
        exchange: ServerWebExchange
    ) = getDeviceReportUseCase.getDeviceReport(reportId)
}
