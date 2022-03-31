package com.mibe.iot.thinker.app.device.to.mqtt

data class DeviceReportModel(
    val reportType: String,
    val reportData: Map<String, String>
)
