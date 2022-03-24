package com.mibe.iot.thinker.domain.discovery

data class DeviceConfigurationCallbacks(
    val onConfigurationSucceeded: () -> Unit,
    val onConfigurationFailed: () -> Unit
)