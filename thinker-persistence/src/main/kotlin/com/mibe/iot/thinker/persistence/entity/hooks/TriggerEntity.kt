package com.mibe.iot.thinker.persistence.entity.hooks

import org.springframework.data.mongodb.core.mapping.Document

@Document("triggers")
data class TriggerEntity (
    var id: String?,
    val name: String,
    val deviceId: String,
    val hookId: String,
    val reportType: String
)