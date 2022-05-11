package com.mibe.iot.thinker.app.settings.from.persistence

import com.mibe.iot.thinker.domain.settings.AppSettings
import com.mibe.iot.thinker.domain.settings.Settings
import com.mibe.iot.thinker.domain.settings.SettingsType
import com.mibe.iot.thinker.persistence.entity.AppSettingsEntity
import com.mibe.iot.thinker.persistence.repository.SpringDataAppSettingsRepository
import com.mibe.iot.thinker.service.settings.port.AppSettingsPort
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import mu.KotlinLogging
import org.bson.Document
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Component

@Component
class AppSettingsAdapter(
    private val appSettingsRepository: SpringDataAppSettingsRepository,
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) : AppSettingsPort {
    private val log = KotlinLogging.logger {}

    override suspend fun updateSettings(appSettings: AppSettings) {
        val settingsEntity: AppSettingsEntity =
            appSettingsRepository.findByType(SettingsType.APPLICATION).awaitSingleOrNull()
                ?: AppSettingsEntity(type = SettingsType.APPLICATION)
        appSettings.transferToEntity(settingsEntity)
        appSettingsRepository.save(settingsEntity).subscribe()
        log.info { "Successfully updated application settings" }
    }

    override suspend fun getAppSettings(): AppSettings? {
        return appSettingsRepository.findByType(SettingsType.APPLICATION).awaitSingleOrNull()?.toAppSettings()
    }

    override suspend fun updateSettings(settings: Settings) {
        val query = getSettingsByTypeQuery(settings.type)
        val doc = Document()
        reactiveMongoTemplate.converter.write(settings, doc)
        val update = Update.fromDocument(doc)
        reactiveMongoTemplate.upsert(query, update, "settings")
            .awaitFirstOrNull()
    }

    override suspend fun getSettingsByType(type: SettingsType): Settings? {
        return reactiveMongoTemplate.find(
            getSettingsByTypeQuery(type),
            Settings::class.java,
            "settings"
        ).awaitFirstOrNull()
    }

    private fun getSettingsByTypeQuery(type: SettingsType) = Query.query(Criteria.where("type").`is`(type))

    override suspend fun settingsWithTypeExist(type: SettingsType): Boolean {
        return reactiveMongoTemplate.exists(
            Query.query(Criteria.where("type").`is`(type)),
            "settings"
        ).awaitSingleOrNull() ?: false
    }
}

