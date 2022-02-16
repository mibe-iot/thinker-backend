package com.mibe.iot.thinker.device.adapter.from.persistance

import com.mibe.iot.thinker.device.application.port.from.GetDeviceReportPort
import com.mibe.iot.thinker.device.application.port.from.SaveDeviceReportPort
import com.mibe.iot.thinker.domain.device.DeviceReport
import com.mibe.iot.thinker.persistence.repository.SpringDataDeviceReportRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class DeviceReportPersistenceAdapter
@Autowired constructor(
    private val deviceReportRepository: SpringDataDeviceReportRepository
) : SaveDeviceReportPort, GetDeviceReportPort {

    override fun saveReport(report: Mono<DeviceReport>): Mono<DeviceReport> {
        return report.flatMap { deviceReportRepository.save(it.toDeviceReportEntity()) }
            .flatMap { entity -> entity.toDeviceReport().toMono() }
    }

    override fun getById(reportId: String): Mono<DeviceReport> {
        return deviceReportRepository.findById(reportId).flatMap {
            it.toDeviceReport().toMono()
        }
    }

    override fun getByDeviceId(deviceId: String, pageable: Pageable): Flux<DeviceReport> {
        return deviceReportRepository.findByDeviceId(deviceId, pageable).flatMap {
            it.toDeviceReport().toMono()
        }
    }
}
