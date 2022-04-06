package com.mibe.iot.thinker.app.device.to.web

import com.mibe.iot.thinker.app.device.to.web.dto.ReportsPageModel
import com.mibe.iot.thinker.app.device.to.web.exception.DeviceReportIllegalPageException
import com.mibe.iot.thinker.app.message.MessageService
import com.mibe.iot.thinker.app.web.ErrorData
import com.mibe.iot.thinker.service.device.DeleteDeviceReportUseCase
import com.mibe.iot.thinker.service.device.GetDeviceReportUseCase
import com.mibe.iot.thinker.service.device.exception.DeviceReportNotFoundException
import kotlinx.coroutines.flow.toList
import mu.KotlinLogging
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
import kotlin.math.ceil

@RestController
@RequestMapping("/api/devices/{deviceId}/reports")
internal class DeviceReportController
@Autowired constructor(
    private val getDeviceReportUseCase: GetDeviceReportUseCase,
    private val deleteDeviceReportUseCase: DeleteDeviceReportUseCase,
    private val messageService: MessageService
) {
    private val log = KotlinLogging.logger {}

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getReportsByDeviceId(
        @PathVariable deviceId: String,
        @RequestParam(required = true) page: Int,
        @RequestParam(required = false, defaultValue = "10") pageSize: Int
    ): ReportsPageModel {
        val reportsCount = getDeviceReportUseCase.getReportsCountByDeviceId(deviceId)
        log.debug{"device id = $deviceId, reports count: $reportsCount"}
        if (page < 1 || page > ceil(reportsCount/pageSize.toDouble())) {
            throw DeviceReportIllegalPageException(page, deviceId)
        }
        val reports = getDeviceReportUseCase.getDeviceReportsByDeviceId(deviceId, page, pageSize)
        return ReportsPageModel(
            reports = reports.toList(),
            page = page,
            pageSize = pageSize,
            itemsCount = reportsCount
        )
    }

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

    @ExceptionHandler(DeviceReportIllegalPageException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleDeviceReportIllegalPage(exception: DeviceReportIllegalPageException, locale: Locale): ErrorData {
        return ErrorData(
            description = messageService.getErrorMessage(
                REPORT_ILLEGAL_PAGE_ERROR,
                locale,
                exception.page,
                exception.deviceId
            ),
            descriptionKey = REPORT_ILLEGAL_PAGE_ERROR,
            httpStatus = HttpStatus.NOT_FOUND.value()
        )
    }

    companion object {
        const val REPORT_NOT_FOUND_ERROR = "device.report.not.found"
        const val REPORT_ILLEGAL_PAGE_ERROR = "device.report.illegal.page"
    }

}
