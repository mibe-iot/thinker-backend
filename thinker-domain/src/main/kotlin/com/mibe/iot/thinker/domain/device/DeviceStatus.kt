package com.mibe.iot.thinker.domain.device

enum class DeviceStatus {
    WAITING_CONFIGURATION,
    WIFI_SHARED,
    CONFIGURED,
    WIFI_SHARING_FAILED,
    BLOCKED
}