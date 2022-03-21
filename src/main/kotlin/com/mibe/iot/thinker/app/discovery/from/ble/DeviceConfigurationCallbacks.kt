package com.mibe.iot.thinker.app.discovery.from.ble

class DeviceConfigurationCallbacks(
    val onConfigurationCompleted: () -> Unit,
    val onConfigurationFailed: () -> Unit
)