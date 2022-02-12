package com.mibe.iot.thinker.discovery.domain

data class DeviceConnectionData(
    val deviceName: String,
    val ssid: String,
    val password: String
)