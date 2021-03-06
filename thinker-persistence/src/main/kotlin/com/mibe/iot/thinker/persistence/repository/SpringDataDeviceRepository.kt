package com.mibe.iot.thinker.persistence.repository

import com.mibe.iot.thinker.domain.device.DeviceStatus
import com.mibe.iot.thinker.persistence.entity.DeviceEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface SpringDataDeviceRepository : ReactiveMongoRepository<DeviceEntity, String> {
    fun existsByName(name: String): Mono<Boolean>
    fun existsByAddress(address: String): Mono<Boolean>
    fun findByAddress(address: String): Mono<DeviceEntity>
    fun findByStatus(status: DeviceStatus): Flux<DeviceEntity>
    fun findAllByConfigurationHashNot(exceptThisHash: Int): Flux<DeviceEntity>
    fun findAllByStatusIn(allowedStatuses: Set<DeviceStatus>): Flux<DeviceEntity>
}
