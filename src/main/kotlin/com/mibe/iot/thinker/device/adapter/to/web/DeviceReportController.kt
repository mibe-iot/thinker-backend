package com.mibe.iot.thinker.device.adapter.to.web

import com.mibe.iot.thinker.device.adapter.to.web.dto.DeviceReportDto
import com.mibe.iot.thinker.device.adapter.to.web.dto.toDeviceReport
import com.mibe.iot.thinker.device.application.port.to.GetDeviceReportUseCase
import com.mibe.iot.thinker.device.application.port.to.SaveDeviceReportUseCase
import com.mibe.iot.thinker.device.application.port.to.exception.DeviceReportNotFoundException
import com.mibe.iot.thinker.domain.device.DeviceReport
import com.mibe.iot.thinker.message.application.MessageService
import com.mibe.iot.thinker.web.error.ErrorData
import java.util.*
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("/api/devices/{deviceId}/reports")
internal class DeviceReportController @Autowired constructor(
    private val saveDeviceReportUseCase: SaveDeviceReportUseCase,
    private val getDeviceReportUseCase: GetDeviceReportUseCase,
    private val messageService: MessageService
) {

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getReportsByDeviceId(
        @PathVariable deviceId: String,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") pageSize: Int
    ) = getDeviceReportUseCase.getDeviceReportsByDeviceId(deviceId, page, pageSize)

    @GetMapping("/{reportId}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getReport(@PathVariable deviceId: String, @PathVariable reportId: String) =
        getDeviceReportUseCase.getDeviceReport(reportId, deviceId)

    @PostMapping("")
    suspend fun saveReport(
        @PathVariable deviceId: String,
        @RequestBody deviceReportDto: DeviceReportDto
    ): DeviceReport {
        return saveDeviceReportUseCase.saveReport(deviceReportDto.toDeviceReport(deviceId))
    }

    @ExceptionHandler(DeviceReportNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleDeviceReportNotFound(exception: DeviceReportNotFoundException, locale: Locale): ErrorData {
        return ErrorData(
            description = messageService.getErrorMessage(
                REPORT_NOT_FOUND_ERROR,
                locale,
                exception.reportId,
                exception.deviceId
            ),
            descriptionKey = REPORT_NOT_FOUND_ERROR,
            httpStatus = HttpStatus.NOT_FOUND.value()
        )
    }

    companion object {
        const val REPORT_NOT_FOUND_ERROR = "device.report.not.found"
    }

}
