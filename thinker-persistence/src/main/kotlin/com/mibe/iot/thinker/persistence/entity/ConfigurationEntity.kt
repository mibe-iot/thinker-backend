package com.mibe.iot.thinker.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("configurations")
class ConfigurationEntity(
    @Id
    var id: String?,
    var parameters: Map<String, String> = emptyMap()
)