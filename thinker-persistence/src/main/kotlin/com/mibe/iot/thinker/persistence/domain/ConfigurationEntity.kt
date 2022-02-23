package com.mibe.iot.thinker.persistence.domain

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
) {
    enum class ConfigurationType {
        WIFI
    }
}