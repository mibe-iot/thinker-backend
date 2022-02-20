package com.mibe.iot.thinker.device.adapter.from.persistance

import com.mibe.iot.thinker.device.application.port.from.DeleteDeviceReportPort
import com.mibe.iot.thinker.device.application.port.from.GetDeviceReportPort
import com.mibe.iot.thinker.device.application.port.from.SaveDeviceReportPort
import com.mibe.iot.thinker.domain.device.DeviceReport
import com.mibe.iot.thinker.persistence.domain.DeviceReportEntity
import com.mibe.iot.thinker.persistence.repository.SpringDataDeviceReportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class DeviceReportPersistenceAdapter
@Autowired constructor(
    private val deviceReportRepository: SpringDataDeviceReportRepository
) : SaveDeviceReportPort, GetDeviceReportPort, DeleteDeviceReportPort {

    override suspend fun saveReport(report: DeviceReport): DeviceReport {
        return deviceReportRepository.save(report.toDeviceReportEntity()).awaitSingle().toDeviceReport()
    }

    override suspend fun getByIdOrNull(reportId: String, deviceId: String): DeviceReport? {
        return deviceReportRepository.findByIdAndDeviceId(reportId, deviceId).awaitSingleOrNull()?.toDeviceReport()
    }

    override fun getByDeviceId(deviceId: String, pageable: Pageable): Flow<DeviceReport> {
        return deviceReportRepository.findByDeviceId(deviceId, pageable).asFlow()
            .map(DeviceReportEntity::toDeviceReport)
    }

    override suspend fun existsById(reportId: String): Boolean =
        deviceReportRepository.existsById(reportId).awaitSingle()

    override suspend fun deleteReport(reportId: String) {
        deviceReportRepository.deleteById(reportId).awaitSingle()
    }
}
