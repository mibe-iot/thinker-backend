package com.mibe.iot.thinker.discovery.domain

data class DeviceConnectionData(
    val address: String,
    var deviceName: String,
    val ssid: String,
    val password: String
)