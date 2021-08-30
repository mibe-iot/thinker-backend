package com.mibe.iot.thinker.device.adapter.from.persistance

import com.mibe.iot.thinker.device.adapter.from.persistance.entity.DeviceEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface SpringDataDeviceRepository : ReactiveMongoRepository<DeviceEntity, String> {
    fun existsByName(name: String): Mono<Boolean>
}
