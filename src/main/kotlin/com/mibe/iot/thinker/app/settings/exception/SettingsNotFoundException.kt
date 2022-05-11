package com.mibe.iot.thinker.app.settings.exception

import com.mibe.iot.thinker.domain.settings.SettingsType

class SettingsNotFoundException(settingsType: SettingsType) : Exception("$settingsType.name settings not found")