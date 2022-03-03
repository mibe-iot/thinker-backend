package com.mibe.iot.thinker.app.device

import com.mibe.iot.thinker.service.device.port.SaveDeviceReportPort
import com.mibe.iot.thinker.service.device.SaveDeviceReportUseCase
import com.mibe.iot.thinker.service.device.exception.DeviceNotFoundException
import com.mibe.iot.thinker.domain.device.DeviceReport
import com.mibe.iot.thinker.service.device.port.GetDevicePort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SaveDeviceReportService
@Autowired constructor(
    private val getDevicePort: GetDevicePort,
    private val saveDeviceReportPort: SaveDeviceReportPort
) : SaveDeviceReportUseCase {

    /**
     * Saves report to persistent storage by using [SaveDeviceReportPort]
     *
     * @param report [DeviceReport]
     * @return saved [DeviceReport]
     */
    override suspend fun saveReport(report: DeviceReport): DeviceReport {
        if (!getDevicePort.existsWithId(report.deviceId)) throw DeviceNotFoundException(
            report.deviceId
        )
        return saveDeviceReportPort.saveReport(report)
    }
}
