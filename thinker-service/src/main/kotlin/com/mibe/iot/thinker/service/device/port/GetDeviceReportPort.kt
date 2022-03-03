package com.mibe.iot.thinker.service.device.port

import com.mibe.iot.thinker.domain.device.DeviceReport
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable

interface GetDeviceReportPort {

    suspend fun getByIdOrNull(reportId: String, deviceId: String): DeviceReport?
    fun getByDeviceId(deviceId: String, pageable: Pageable): Flow<DeviceReport>
    suspend fun existsById(reportId: String): Boolean
}
