package com.mibe.iot.thinker.app.device.to.web.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.mibe.iot.thinker.domain.device.DeviceAction
import com.mibe.iot.thinker.domain.device.DeviceUpdates

data class DeviceDto(
    val name: String?,
    val description: String?,
)

fun DeviceDto.toDeviceUpdates() = DeviceUpdates(
    name = name,
    description = description,
)
