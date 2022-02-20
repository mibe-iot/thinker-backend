package com.mibe.iot.thinker.device.application.port.from

import com.mibe.iot.thinker.domain.device.DeviceReport
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Flux

interface GetDeviceReportPort {

    suspend fun getByIdOrNull(reportId: String, deviceId: String): DeviceReport?
    fun getByDeviceId(deviceId: String, pageable: Pageable): Flow<DeviceReport>
    suspend fun existsById(reportId: String): Boolean
}
