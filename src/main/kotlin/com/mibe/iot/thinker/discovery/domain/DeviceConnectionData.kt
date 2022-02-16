package com.mibe.iot.thinker.discovery.domain

data class DeviceConnectionData(
    val ssid: ByteArray,
    val password: ByteArray
)