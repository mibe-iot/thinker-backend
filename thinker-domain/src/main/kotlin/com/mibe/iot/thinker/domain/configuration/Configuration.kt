package com.mibe.iot.thinker.domain.configuration

sealed class Configuration(
    val type: ConfigurationType
)

class WifiConfiguration(
    val ssid: String,
    val password: String
) : Configuration(ConfigurationType.WIFI) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WifiConfiguration

        if (ssid != other.ssid) return false
        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ssid.hashCode()
        result = 31 * result + password.hashCode()
        return result
    }

}