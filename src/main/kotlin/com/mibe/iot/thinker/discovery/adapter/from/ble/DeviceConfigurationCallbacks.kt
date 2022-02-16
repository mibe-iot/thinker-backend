package com.mibe.iot.thinker.discovery.adapter.from.ble

class DeviceConfigurationCallbacks(
    val onConfigurationCompleted: () -> Unit,
    val onConfigurationFailed: () -> Unit
)