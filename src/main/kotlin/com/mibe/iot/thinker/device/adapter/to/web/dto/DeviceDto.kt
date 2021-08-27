package com.mibe.iot.thinker.device.adapter.to.web.dto

import com.mibe.iot.thinker.device.domain.DeviceUpdates
import net.minidev.json.annotate.JsonIgnore

data class DeviceDto(
    @JsonIgnore
    var id: String?,
    val name: String?,
    val description: String?,
    @JsonIgnore
    val ip: String?
)

fun DeviceDto.toDeviceUpdates() = DeviceUpdates(
    id!!,
    name,
    description,
    ip
)
