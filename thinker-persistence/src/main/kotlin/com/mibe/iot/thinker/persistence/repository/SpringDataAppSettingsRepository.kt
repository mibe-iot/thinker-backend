package com.mibe.iot.thinker.persistence.repository

import com.mibe.iot.thinker.domain.settings.SettingsType
import com.mibe.iot.thinker.persistence.entity.AppSettingsEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface SpringDataAppSettingsRepository : ReactiveMongoRepository<AppSettingsEntity, String> {
    fun findByType(type: SettingsType): Mono<AppSettingsEntity>
}