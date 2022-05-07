package com.mibe.iot.thinker.app.settings

import com.mibe.iot.thinker.app.settings.exception.InvalidAppSettingsException
import com.mibe.iot.thinker.app.validation.DEVICE_NAME_MIN_LENGTH
import com.mibe.iot.thinker.domain.settings.AppSettings

const val WIFI_PASSWORD_MIN_LENGTH = 8
const val WIFI_SSID_MIN_LENGTH = 2

fun validateAppSettings(appSettings: AppSettings) {
    val errors = mutableListOf<Pair<String, String>>()
    if (appSettings.password.size < WIFI_PASSWORD_MIN_LENGTH) {
        errors.add(Pair("validation.settings.app.password.min.length|||$DEVICE_NAME_MIN_LENGTH", "password"))
    }
    if (appSettings.ssid.size < WIFI_SSID_MIN_LENGTH) {
        errors.add(Pair("validation.settings.app.ssid.min.length|||$WIFI_SSID_MIN_LENGTH", "ssid"))
    }

    if (errors.isNotEmpty()) {
        throw InvalidAppSettingsException(errors)
    }
}