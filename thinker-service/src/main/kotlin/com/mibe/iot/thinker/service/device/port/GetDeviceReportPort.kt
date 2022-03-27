package com.mibe.iot.thinker.service.device.port

import com.mibe.iot.thinker.domain.data.ItemsPage
import com.mibe.iot.thinker.domain.device.DeviceReport
import kotlinx.coroutines.flow.Flow

interface GetDeviceReportPort {

    suspend fun getByIdOrNull(reportId: String, deviceId: String): DeviceReport?
    fun getByDeviceId(deviceId: String, itemsPage: ItemsPage): Flow<DeviceReport>
    suspend fun getLatestDeviceReport(deviceId: String): DeviceReport?
    suspend fun existsById(reportId: String): Boolean

}
