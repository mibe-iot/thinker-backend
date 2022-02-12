package com.mibe.iot.thinker.persistence.repository

import com.mibe.iot.thinker.persistence.domain.DeviceReportEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface SpringDataDeviceReportRepository : ReactiveMongoRepository<DeviceReportEntity, String> {
    fun findByDeviceId(deviceId: String, pageable: Pageable): Flux<DeviceReportEntity>
}
