package com.mibe.iot.thinker.discovery.adapter.from.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("devices")
class ConnectedDeviceEntity(
    @Id
    var id: String? = null,
    val name: String,
    val address: String
)