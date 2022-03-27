package com.mibe.iot.thinker.app.device

import com.mibe.iot.thinker.service.device.port.SaveDeviceReportPort
import com.mibe.iot.thinker.service.device.SaveDeviceReportUseCase
import com.mibe.iot.thinker.service.device.exception.DeviceNotFoundException
import com.mibe.iot.thinker.domain.device.DeviceReport
import com.mibe.iot.thinker.domain.exception.ReportCreationException
import com.mibe.iot.thinker.service.device.port.GetDevicePort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SaveDeviceReportService
@Autowired constructor(
    private val getDevicePort: GetDevicePort,
    private val saveDeviceReportPort: SaveDeviceReportPort
) : SaveDeviceReportUseCase {

    override suspend fun saveReport(deviceId: String, reportData: Map<String, String>): DeviceReport {
        if (getDevicePort.existsWithId(deviceId).not()) { throw DeviceNotFoundException(deviceId) }
        if (reportData.isEmpty()) { throw ReportCreationException("Report's data is empty") }
        val deviceReport = DeviceReport(
            id = null,
            deviceId = deviceId,
            reportData = reportData,
            dateTimeCreated = LocalDateTime.now()
        )
        return saveDeviceReportPort.saveReport(deviceReport)
    }
}
