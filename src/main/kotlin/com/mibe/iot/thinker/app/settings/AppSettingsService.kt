package com.mibe.iot.thinker.app.settings

import com.mibe.iot.thinker.domain.settings.AppSettings
import com.mibe.iot.thinker.domain.settings.MailSettings
import com.mibe.iot.thinker.domain.settings.Settings
import com.mibe.iot.thinker.domain.settings.SettingsType
import com.mibe.iot.thinker.service.settings.AppSettingsUseCase
import com.mibe.iot.thinker.service.settings.port.AppSettingsPort
import org.springframework.stereotype.Service

@Service
class AppSettingsService(
    private val appSettingsPort: AppSettingsPort
) : AppSettingsUseCase {

    override suspend fun updateSettings(appSettings: AppSettings) {
        validateAppSettings(appSettings)
        appSettingsPort.updateSettings(appSettings)
    }

    override suspend fun getAppSettings(): AppSettings? {
        return appSettingsPort.getAppSettings()
    }

    override suspend fun updateSettings(settings: Settings) {
        appSettingsPort.updateSettings(settings)
    }

    override suspend fun getSettings(type: SettingsType): Settings? {
        return appSettingsPort.getSettingsByType(type)
    }
}