package com.mibe.iot.thinker.domain.device

data class DeviceWithReport(
    val device: Device,
    val deviceReport: DeviceReport?
)
