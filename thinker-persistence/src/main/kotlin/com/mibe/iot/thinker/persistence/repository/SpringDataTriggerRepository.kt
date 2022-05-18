package com.mibe.iot.thinker.persistence.repository

import com.mibe.iot.thinker.persistence.entity.hooks.TriggerEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface SpringDataTriggerRepository: ReactiveMongoRepository<TriggerEntity, String> {
    fun findAllByDeviceIdAndHookIdIn(deviceId: String, hookIds: List<String>): Flux<TriggerEntity>
    fun findByDeviceIdAndReportType(deviceId: String, reportType: String): Mono<TriggerEntity>

    fun findAllByDeviceId(deviceId: String): Flux<TriggerEntity>

    fun deleteAllByHookId(hookId: String): Mono<Void>

    fun deleteAllByDeviceId(deviceId: String): Mono<Void>
}