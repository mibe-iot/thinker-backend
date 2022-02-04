package com.mibe.iot.thinker.device.application

import com.mibe.iot.thinker.device.application.port.from.SaveDeviceReportPort
import com.mibe.iot.thinker.device.application.port.to.SaveDeviceReportUseCase
import com.mibe.iot.thinker.device.domain.DeviceReport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SaveDeviceReportService @Autowired constructor(
    private val saveDeviceReportPort: SaveDeviceReportPort
) : SaveDeviceReportUseCase {

    /**
     * Saves report to persistent storage by using [SaveDeviceReportPort]
     *
     * @param report [Mono] of [DeviceReport]
     * @return [Mono] of saved [DeviceReport]
     */
    override fun saveReport(report: Mono<DeviceReport>): Mono<DeviceReport> {
        return saveDeviceReportPort.saveReport(report)
    }
}
