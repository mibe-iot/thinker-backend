package com.mibe.iot.thinker.device.adapter.to.web.model

import com.mibe.iot.thinker.device.domain.DeviceAction
import org.springframework.hateoas.RepresentationModel

data class DeviceModel(
    var id: String,
    var name: String,
    var description: String,
    var ip: String?,
    var actions: Set<DeviceAction> = setOf()
) : RepresentationModel<DeviceModel>()
