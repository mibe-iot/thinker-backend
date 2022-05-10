package com.mibe.iot.thinker.persistence.repository

import com.mibe.iot.thinker.persistence.entity.hooks.TriggerEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface SpringDataTriggerRepository: ReactiveMongoRepository<TriggerEntity, String> {
    fun findAllByDeviceIdAndHookIdIn(deviceId: String, hookIds: List<String>): Flux<TriggerEntity>
}