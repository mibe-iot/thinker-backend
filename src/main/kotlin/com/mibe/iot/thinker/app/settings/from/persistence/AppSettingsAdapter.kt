package com.mibe.iot.thinker.app.settings.from.persistence

import com.mibe.iot.thinker.domain.settings.AppSettings
import com.mibe.iot.thinker.domain.settings.SettingsType
import com.mibe.iot.thinker.persistence.entity.AppSettingsEntity
import com.mibe.iot.thinker.persistence.repository.SpringDataAppSettingsRepository
import com.mibe.iot.thinker.service.settings.port.AppSettingsPort
import kotlinx.coroutines.reactor.awaitSingleOrNull
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class AppSettingsAdapter(
    private val appSettingsRepository: SpringDataAppSettingsRepository
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
}

