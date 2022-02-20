package com.mibe.iot.thinker.device.application

import com.mibe.iot.thinker.device.application.port.from.GetDevicePort
import com.mibe.iot.thinker.device.application.port.from.GetDeviceReportPort
import com.mibe.iot.thinker.device.application.port.to.GetDeviceReportUseCase
import com.mibe.iot.thinker.device.application.port.to.exception.DeviceNotFoundException
import com.mibe.iot.thinker.device.application.port.to.exception.DeviceReportNotFoundException
import com.mibe.iot.thinker.domain.device.DeviceReport
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class GetDeviceReportService
@Autowired constructor(
    private val getDevicePort: GetDevicePort,
    private val getDeviceReportPort: GetDeviceReportPort
) : GetDeviceReportUseCase {

    override suspend fun getDeviceReport(reportId: String, deviceId: String): DeviceReport {
        return getDeviceReportPort.getByIdOrNull(reportId, deviceId)
            ?: throw DeviceReportNotFoundException(reportId, deviceId)
    }

    override suspend fun getDeviceReportsByDeviceId(deviceId: String, page: Int, pageSize: Int): Flow<DeviceReport> {
        if (!getDevicePort.existsWithId(deviceId)) throw DeviceNotFoundException(deviceId)
        val pageable = PageRequest.of(page, pageSize, Sort.by("dateTimeCreated"))
        return getDeviceReportPort.getByDeviceId(deviceId, pageable)
    }
}
