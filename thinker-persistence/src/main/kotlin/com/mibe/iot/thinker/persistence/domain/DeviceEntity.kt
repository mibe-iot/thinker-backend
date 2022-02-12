package com.mibe.iot.thinker.persistence.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("devices")
class DeviceEntity(
    @Id
    var id: String? = null,
    val name: String,
    val address: String,
    val description: String = "",
    val actions: Set<DeviceActionEntity> = emptySet()
)
