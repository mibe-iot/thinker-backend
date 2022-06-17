package com.mibe.iot.thinker.app.device

import com.mibe.iot.thinker.service.device.DeleteDeviceReportUseCase
import com.mibe.iot.thinker.service.device.exception.DeviceReportNotFoundException
import com.mibe.iot.thinker.service.device.port.DeleteDeviceReportPort
import com.mibe.iot.thinker.service.device.port.GetDeviceReportPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DeleteDeviceReportService
@Autowired constructor(
    private val getDeviceReportPort: GetDeviceReportPort,
    private val deleteDeviceReportPort: DeleteDeviceReportPort
) : DeleteDeviceReportUseCase {

    override suspend fun deleteReport(reportId: String, deviceId: String) {
        if (getDeviceReportPort.existsById(reportId)) {
            deleteDeviceReportPort.deleteReport(reportId)
        } else {
            throw DeviceReportNotFoundException(
                reportId,
                deviceId
            )
        }
    }

}