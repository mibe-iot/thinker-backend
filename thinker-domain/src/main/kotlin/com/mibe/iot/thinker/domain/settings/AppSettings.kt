package com.mibe.iot.thinker.domain.settings

/**
 * The application settings like Wi-Fi configuration
 */
data class AppSettings(
    val ssid: ByteArray,
    val password: ByteArray
): Settings(SettingsType.APPLICATION) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AppSettings

        if (!ssid.contentEquals(other.ssid)) return false
        if (!password.contentEquals(other.password)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ssid.contentHashCode()
        result = 31 * result + password.contentHashCode()
        return result
    }

}
