package com.mibe.iot.thinker.domain.configuration

sealed class Configuration(
    val type: ConfigurationType
)

class WifiConfiguration(
    val ssid: String,
    val password: String
) : Configuration(ConfigurationType.WIFI)