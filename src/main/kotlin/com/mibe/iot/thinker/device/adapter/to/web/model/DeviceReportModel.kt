package com.mibe.iot.thinker.device.adapter.to.web.model

import com.fasterxml.jackson.annotation.JsonRootName
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import java.time.LocalDateTime

@JsonRootName("report")
@Relation(collectionRelation = "reports")
class DeviceReportModel (
    var id: String,
    var deviceId: String,
    var reportData: Map<String, String>,
    var dateTimeCreated: LocalDateTime
) : RepresentationModel<DeviceReportModel>()
