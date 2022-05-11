package com.mibe.iot.thinker.app.settings.from.persistence

import com.mibe.iot.thinker.domain.settings.AppSettings
import com.mibe.iot.thinker.persistence.entity.AppSettingsEntity

fun AppSettings.transferToEntity(entity: AppSettingsEntity) = run {
    entity.ssid = ssid.copyOf()
    entity.password = password.copyOf()
}

fun AppSettingsEntity.toAppSettings() = AppSettings(
    ssid = ssid.copyOf(),
    password = password.copyOf()
)