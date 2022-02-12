package com.mibe.iot.thinker.persistence.repository

import com.mibe.iot.thinker.persistence.domain.DeviceEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface SpringDataDeviceRepository : ReactiveMongoRepository<DeviceEntity, String> {
    fun existsByName(name: String): Mono<Boolean>
}
