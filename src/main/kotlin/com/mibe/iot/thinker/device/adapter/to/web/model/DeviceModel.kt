package com.mibe.iot.thinker.device.adapter.to.web.model

import com.fasterxml.jackson.annotation.JsonRootName
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@JsonRootName("device")
@Relation(collectionRelation = "devices")
data class DeviceModel(
    var id: String,
    var name: String,
    var description: String,
    var mac: String?,
    var actions: Set<DeviceActionModel> = setOf()
) : RepresentationModel<DeviceModel>()
