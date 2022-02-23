package com.mibe.iot.thinker.persistence.repository

import com.mibe.iot.thinker.persistence.domain.ConfigurationEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface SpringDataConfigurationsRepository : ReactiveMongoRepository<ConfigurationEntity, String> {
    fun getByType(type: ConfigurationEntity.ConfigurationType): Mono<ConfigurationEntity>
}