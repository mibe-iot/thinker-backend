package com.mibe.iot.thinker.device.adapter.to.web.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.mibe.iot.thinker.device.domain.DeviceAction
import com.mibe.iot.thinker.device.domain.DeviceUpdates

data class DeviceDto(
    @JsonIgnore
    var id: String?,
    val name: String?,
    val description: String?,
    @JsonIgnore
    var mac: String?,
    val actions: Set<DeviceAction>?
)

fun DeviceDto.toDeviceUpdates() = DeviceUpdates(
    id!!,
    name,
    description,
    mac,
    actions
)
