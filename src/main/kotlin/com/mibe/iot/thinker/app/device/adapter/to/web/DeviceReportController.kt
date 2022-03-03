package com.mibe.iot.thinker.app.device.adapter.to.web

import com.mibe.iot.thinker.service.device.application.port.to.DeleteDeviceReportUseCase
import com.mibe.iot.thinker.service.device.application.port.to.GetDeviceReportUseCase
import com.mibe.iot.thinker.service.device.application.port.to.exception.DeviceReportNotFoundException
import com.mibe.iot.thinker.message.application.MessageService
import com.mibe.iot.thinker.web.error.ErrorData
import java.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/devices/{deviceId}/reports")
internal class DeviceReportController @Autowired constructor(
    private val getDeviceReportUseCase: GetDeviceReportUseCase,
    private val deleteDeviceReportUseCase: DeleteDeviceReportUseCase,
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

    @DeleteMapping("/{reportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteReport(
        @PathVariable(name = "deviceId") deviceId: String,
        @PathVariable(name = "reportId") reportId: String
    ) = deleteDeviceReportUseCase.deleteReport(reportId, deviceId)

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
