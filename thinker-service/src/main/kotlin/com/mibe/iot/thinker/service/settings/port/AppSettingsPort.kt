package com.mibe.iot.thinker.service.settings.port

import com.mibe.iot.thinker.domain.settings.AppSettings
import com.mibe.iot.thinker.domain.settings.Settings
import com.mibe.iot.thinker.domain.settings.SettingsType
import kotlinx.coroutines.flow.Flow

/**
 * Adapts service calls to persistence layer
 */
interface AppSettingsPort {

    /**
     * Updates application settings by all fields from given object
     * @param appSettings application settings
     */
    suspend fun updateSettings(appSettings: AppSettings)

    /**
     * Retrieves application settings or null if they are not present
     * @return application settings or null
     */
    suspend fun getAppSettings(): AppSettings?

    /**
     * Updates settings by all fields from given object
     * @param settings settings
     */
    suspend fun updateSettings(settings: Settings)

    /**
     * Retrieves settings or null if they are not present
     * @return settings or null
     */
    suspend fun getSettingsByType(type: SettingsType): Settings?

    suspend fun settingsWithTypeExist(type: SettingsType): Boolean

    suspend fun getAllConfiguredSettingTypes(): Flow<SettingsType>

}