package com.mibe.iot.thinker.app.device

import com.mibe.iot.thinker.domain.data.ItemsPage
import com.mibe.iot.thinker.domain.device.DeviceReport
import com.mibe.iot.thinker.service.device.GetDeviceReportUseCase
import com.mibe.iot.thinker.service.device.exception.DeviceNotFoundException
import com.mibe.iot.thinker.service.device.exception.DeviceReportNotFoundException
import com.mibe.iot.thinker.service.device.port.GetDevicePort
import com.mibe.iot.thinker.service.device.port.GetDeviceReportPort
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GetDeviceReportService
@Autowired constructor(
    private val getDevicePort: GetDevicePort,
    private val getDeviceReportPort: GetDeviceReportPort
) : GetDeviceReportUseCase {

    override suspend fun getDeviceReport(reportId: String, deviceId: String): DeviceReport {
        return getDeviceReportPort.getByIdOrNull(reportId, deviceId)
            ?: throw DeviceReportNotFoundException(
                reportId,
                deviceId
            )
    }

    override suspend fun getDeviceReportsByDeviceId(deviceId: String, page: Int, pageSize: Int): Flow<DeviceReport> {
        if (!getDevicePort.existsWithId(deviceId)) throw DeviceNotFoundException(
            deviceId
        )
        return getDeviceReportPort.getByDeviceId(deviceId, ItemsPage(page, pageSize))
    }
}
