package com.mibe.iot.thinker.app.device.to.web.dto

import com.mibe.iot.thinker.domain.device.DeviceReport

data class ReportsPageModel(
    val reports: List<DeviceReport>,
    val page: Int,
    val pageSize: Int,
    val itemsCount: Long
)
