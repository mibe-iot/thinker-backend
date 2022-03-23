package com.mibe.iot.thinker.app.device.to.web.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.mibe.iot.thinker.domain.device.DeviceAction
import com.mibe.iot.thinker.domain.device.DeviceUpdates

data class DeviceDto(
    @JsonIgnore
    var id: String?,
    val name: String?,
    val description: String?,
    @JsonIgnore
    var address: String?,
    val actions: Set<DeviceAction>?
)

fun DeviceDto.toDeviceUpdates() = DeviceUpdates(
    id = id!!,
    name = name,
    address = null,
    description = description,
    actions = actions
)
