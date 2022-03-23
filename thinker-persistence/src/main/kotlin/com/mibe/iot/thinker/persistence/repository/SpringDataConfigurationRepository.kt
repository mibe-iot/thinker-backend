package com.mibe.iot.thinker.persistence.repository

import com.mibe.iot.thinker.domain.configuration.ConfigurationType
import com.mibe.iot.thinker.persistence.entity.ConfigurationEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface SpringDataConfigurationRepository : ReactiveMongoRepository<ConfigurationEntity, String> {
    fun getByType(type: ConfigurationType): Mono<ConfigurationEntity>
    fun updateByType(configurationEntity: ConfigurationEntity, type: ConfigurationType): Mono<ConfigurationEntity>
}