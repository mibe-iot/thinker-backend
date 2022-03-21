package com.mibe.iot.thinker.persistence.entity

import com.mibe.iot.thinker.domain.configuration.ConfigurationType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("configurations")
class ConfigurationEntity(
    @Id
    var id: String?,
    @Indexed(unique = true)
    val type: ConfigurationType,
    var parameters: Map<String, String> = emptyMap()
)