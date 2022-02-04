package com.mibe.iot.thinker.device.adapter.to.web.model

import org.springframework.hateoas.RepresentationModel

data class DeviceActionModel(
    var name: String,
    var mapping: String,
    var descriptionKey: String
) : RepresentationModel<DeviceActionModel>()
