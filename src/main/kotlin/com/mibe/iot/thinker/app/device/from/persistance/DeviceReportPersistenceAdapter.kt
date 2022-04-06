package com.mibe.iot.thinker.app.device.from.persistance

import com.mibe.iot.thinker.domain.data.ItemsPage
import com.mibe.iot.thinker.domain.device.DeviceReport
import com.mibe.iot.thinker.persistence.entity.DeviceReportEntity
import com.mibe.iot.thinker.persistence.repository.SpringDataDeviceReportRepository
import com.mibe.iot.thinker.service.device.port.DeleteDeviceReportPort
import com.mibe.iot.thinker.service.device.port.GetDeviceReportPort
import com.mibe.iot.thinker.service.device.port.SaveDeviceReportPort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
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

    override fun getByDeviceId(deviceId: String, itemsPage: ItemsPage): Flow<DeviceReport> {
        val pageable = PageRequest.of(itemsPage.page, itemsPage.pageSize, Sort.by("dateTimeCreated").descending())
        return deviceReportRepository.findByDeviceId(deviceId, pageable).asFlow()
            .map(DeviceReportEntity::toDeviceReport)
    }

    override suspend fun getLatestDeviceReport(deviceId: String): DeviceReport? {
        return deviceReportRepository.findTopByDeviceIdOrderByDateTimeCreatedDesc(deviceId).awaitSingleOrNull()
            ?.toDeviceReport()
    }

    override suspend fun existsById(reportId: String): Boolean =
        deviceReportRepository.existsById(reportId).awaitSingle()

    override suspend fun getCountByDeviceId(deviceId: String) = deviceReportRepository.countByDeviceId(deviceId).awaitSingle()

    override suspend fun deleteReport(reportId: String) {
        deviceReportRepository.deleteById(reportId).awaitSingle()
    }
}
