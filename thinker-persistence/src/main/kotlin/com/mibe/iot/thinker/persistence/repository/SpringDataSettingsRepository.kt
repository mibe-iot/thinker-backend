package com.mibe.iot.thinker.persistence.repository

import com.mibe.iot.thinker.persistence.entity.AppSettingsEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface SpringDataSettingsRepository : ReactiveMongoRepository<AppSettingsEntity, String> {
}