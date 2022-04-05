package com.mibe.iot.thinker.persistence.repository

import com.mibe.iot.thinker.persistence.entity.DeviceReportEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface SpringDataDeviceReportRepository : ReactiveMongoRepository<DeviceReportEntity, String> {
    fun findByIdAndDeviceId(reportId: String, deviceId: String): Mono<DeviceReportEntity>
    fun findByDeviceId(deviceId: String, pageable: Pageable): Flux<DeviceReportEntity>
    fun findTopByDeviceIdOrderByDateTimeCreatedDesc(deviceId: String): Mono<DeviceReportEntity>
    fun getCountByDeviceId(deviceId: String): Long
}
