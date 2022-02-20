package com.mibe.iot.thinker.device.application

import com.mibe.iot.thinker.device.application.port.from.GetDeviceReportPort
import com.mibe.iot.thinker.device.application.port.to.GetDeviceReportUseCase
import com.mibe.iot.thinker.device.application.port.to.exception.DeviceReportNotFoundException
import com.mibe.iot.thinker.domain.device.DeviceReport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class GetDeviceReportService
@Autowired constructor(
    private val getDeviceReportPort: GetDeviceReportPort
) : GetDeviceReportUseCase {

    override suspend fun getDeviceReport(reportId: String, deviceId: String): DeviceReport {
        return getDeviceReportPort.getByIdOrNull(reportId, deviceId)
            ?: throw DeviceReportNotFoundException(reportId, deviceId)
    }

    override fun getDeviceReportsByDeviceId(deviceId: String, page: Int, pageSize: Int): Flux<DeviceReport> {
        val pageable = PageRequest.of(page, pageSize, Sort.by("dateTimeCreated"))
        return getDeviceReportPort.getByDeviceId(deviceId, pageable)
    }
}
