package com.mibe.iot.thinker.device.adapter.to.web

import com.mibe.iot.thinker.device.adapter.to.web.dto.DeviceReportDto
import com.mibe.iot.thinker.device.adapter.to.web.dto.toDeviceReport
import com.mibe.iot.thinker.device.adapter.to.web.model.DeviceReportModel
import com.mibe.iot.thinker.device.adapter.to.web.model.assembler.DeviceReportModelAssembler
import com.mibe.iot.thinker.device.application.port.to.GetDeviceReportUseCase
import com.mibe.iot.thinker.device.application.port.to.SaveDeviceReportUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
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
    private val getDeviceReportUseCase: GetDeviceReportUseCase,
    private val deviceReportModelAssembler: DeviceReportModelAssembler
) {

    @PostMapping("")
    fun saveReport(
        @PathVariable deviceId: String,
        @RequestBody deviceReportDto: Mono<DeviceReportDto>,
        exchange: ServerWebExchange
    ): Mono<DeviceReportModel> {
        return deviceReportDto.flatMap { reportDto ->
            saveDeviceReportUseCase.saveReport(reportDto.toDeviceReport(deviceId).toMono())
        }.flatMap {
            deviceReportModelAssembler.toModel(it, exchange).toMono()
        }
    }

    @GetMapping("")
    fun getReportsByDeviceId(
        @PathVariable deviceId: String,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") pageSize: Int,
        exchange: ServerWebExchange
    ): Mono<CollectionModel<DeviceReportModel>> {
        return deviceReportModelAssembler.toCollectionModel(
            getDeviceReportUseCase.getDeviceReportsByDeviceId(
                deviceId,
                page,
                pageSize
            ),
            exchange
        )
    }

    @GetMapping("/{reportId}")
    fun getReport(
        @PathVariable reportId: String,
        exchange: ServerWebExchange
    ): Mono<DeviceReportModel> {
        return getDeviceReportUseCase.getDeviceReport(reportId).flatMap {
            deviceReportModelAssembler.toModel(it, exchange).toMono()
        }
    }
}
