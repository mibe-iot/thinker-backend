package com.mibe.iot.thinker.persistence.repository

import com.mibe.iot.thinker.persistence.entity.ConfigurationEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface SpringDataConfigurationRepository : ReactiveMongoRepository<ConfigurationEntity, String>