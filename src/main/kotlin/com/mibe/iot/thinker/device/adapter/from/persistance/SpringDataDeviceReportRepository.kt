package com.mibe.iot.thinker.device.adapter.from.persistance

import com.mibe.iot.thinker.device.adapter.from.persistance.entity.DeviceReportEntity
import com.mibe.iot.thinker.device.domain.DeviceReport
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface SpringDataDeviceReportRepository : ReactiveMongoRepository<DeviceReportEntity, String> {
    fun findByDeviceId(deviceId: String, pageable: Pageable): Flux<DeviceReportEntity>
}
