package com.mibe.iot.thinker.app.settings

import com.mibe.iot.thinker.app.settings.exception.InvalidAppSettingsException
import com.mibe.iot.thinker.domain.settings.AppSettings
import com.mibe.iot.thinker.domain.settings.MailSettings

const val WIFI_PASSWORD_MIN_LENGTH = 8
const val WIFI_SSID_MIN_LENGTH = 2

const val MAIL_PASSWORD_MIN_LENGTH = 2

fun validateAppSettings(appSettings: AppSettings) {
    validate { errors ->
        if (appSettings.password.size < WIFI_PASSWORD_MIN_LENGTH) {
            errors.add(Pair("validation.settings.app.password.min.length|||$WIFI_PASSWORD_MIN_LENGTH", "password"))
        }
        if (appSettings.ssid.size < WIFI_SSID_MIN_LENGTH) {
            errors.add(Pair("validation.settings.app.ssid.min.length|||$WIFI_SSID_MIN_LENGTH", "ssid"))
        }
    }
}

fun validateMailPassword(mailSettings: MailSettings) {
    validate { errors ->
        if (mailSettings.mailPassword.size < MAIL_PASSWORD_MIN_LENGTH) {
            errors.add(Pair("validation.settings.mail.password.min.length|||$MAIL_PASSWORD_MIN_LENGTH", "password"))
        }
    }
}

private fun validate(validationBlock: (MutableList<Pair<String, String>>) -> Unit) {
    val errors = mutableListOf<Pair<String, String>>()
    validationBlock(errors)
    if (errors.isNotEmpty()) {
        throw InvalidAppSettingsException(errors)
    }
}