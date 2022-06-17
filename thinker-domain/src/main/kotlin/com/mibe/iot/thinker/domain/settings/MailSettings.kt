package com.mibe.iot.thinker.domain.settings

class MailSettings(
    val mailUsername: CharArray,
    val mailPassword: CharArray
) : Settings(SettingsType.MAIL) {
}