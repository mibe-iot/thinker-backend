package com.mibe.iot.thinker.device.application

import com.mibe.iot.thinker.device.application.port.from.DeleteDeviceReportPort
import com.mibe.iot.thinker.device.application.port.from.GetDeviceReportPort
import com.mibe.iot.thinker.device.application.port.to.DeleteDeviceReportUseCase
import com.mibe.iot.thinker.device.application.port.to.exception.DeviceReportNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DeleteDeviceReportService
@Autowired constructor(
    private val getDeviceReportPort: GetDeviceReportPort,
    private val deleteDeviceReportPort: DeleteDeviceReportPort
) : DeleteDeviceReportUseCase {

    override suspend fun deleteReport(reportId: String, deviceId: String) {
        if(getDeviceReportPort.existsById(reportId)) {
            deleteDeviceReportPort.deleteReport(reportId)
        } else {
            throw DeviceReportNotFoundException(reportId, deviceId)
        }
    }

}