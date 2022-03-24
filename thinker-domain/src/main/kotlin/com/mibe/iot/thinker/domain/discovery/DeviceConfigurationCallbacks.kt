package com.mibe.iot.thinker.domain.discovery

data class DeviceConfigurationCallbacks(
    val onConfigurationSucceeded: (configurationHash: Int) -> Unit,
    val onConfigurationFailed: () -> Unit
)