package com.mibe.iot.thinker.domain.device

enum class DeviceStatus {
    WAITING_CONFIGURATION,
    CONFIGURED,
    FAILED_TO_CONFIGURE,
    BLOCKED
}