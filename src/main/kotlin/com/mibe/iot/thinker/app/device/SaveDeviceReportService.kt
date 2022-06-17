package com.mibe.iot.thinker.app.device

import com.mibe.iot.thinker.domain.device.DeviceReport
import com.mibe.iot.thinker.domain.exception.ReportCreationException
import com.mibe.iot.thinker.service.device.SaveDeviceReportUseCase
import com.mibe.iot.thinker.service.device.exception.DeviceNotFoundException
import com.mibe.iot.thinker.service.device.port.GetDevicePort
import com.mibe.iot.thinker.service.device.port.SaveDeviceReportPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SaveDeviceReportService
@Autowired constructor(
    private val getDevicePort: GetDevicePort,
    private val saveDeviceReportPort: SaveDeviceReportPort
) : SaveDeviceReportUseCase {

    override suspend fun saveReport(report: DeviceReport): DeviceReport {
        if (getDevicePort.existsWithId(report.deviceId).not()) {
            throw DeviceNotFoundException(report.deviceId)
        }
        if (report.reportData.isEmpty()) {
            throw ReportCreationException("Report's data is empty")
        }
        report.dateTimeCreated = LocalDateTime.now()
        return saveDeviceReportPort.saveReport(report)
    }
}
