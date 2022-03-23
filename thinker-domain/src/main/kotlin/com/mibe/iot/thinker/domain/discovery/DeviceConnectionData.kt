package com.mibe.iot.thinker.domain.discovery

data class DeviceConnectionData(
    val ssid: ByteArray,
    val password: ByteArray
)