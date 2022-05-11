package com.mibe.iot.thinker.domain.discovery

data class DeviceConnectionData(
    val ssid: CharArray,
    val password: CharArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DeviceConnectionData

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