package com.mibe.iot.thinker.app.discovery.adapter.from.ble

class DeviceConfigurationCallbacks(
    val onConfigurationCompleted: () -> Unit,
    val onConfigurationFailed: () -> Unit
)