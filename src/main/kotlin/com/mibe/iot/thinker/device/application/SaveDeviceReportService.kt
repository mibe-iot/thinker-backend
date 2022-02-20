package com.mibe.iot.thinker.device.application

import com.mibe.iot.thinker.device.application.port.from.GetDevicePort
import com.mibe.iot.thinker.device.application.port.from.SaveDeviceReportPort
import com.mibe.iot.thinker.device.application.port.to.SaveDeviceReportUseCase
import com.mibe.iot.thinker.device.application.port.to.exception.DeviceNotFoundException
import com.mibe.iot.thinker.device.application.port.to.exception.DeviceReportNotFoundException
import com.mibe.iot.thinker.domain.device.DeviceReport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

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
        if (!getDevicePort.existsWithId(report.deviceId)) throw DeviceNotFoundException(report.deviceId)
        return saveDeviceReportPort.saveReport(report)
    }
}
