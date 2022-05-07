package com.mibe.iot.thinker.service.settings.port

import com.mibe.iot.thinker.domain.settings.AppSettings

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
     * @return application settings or nul
     */
    suspend fun getAppSettings(): AppSettings?

}