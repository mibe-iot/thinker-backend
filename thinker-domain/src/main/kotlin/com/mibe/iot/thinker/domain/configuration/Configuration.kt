package com.mibe.iot.thinker.domain.configuration

data class Configuration(
    var id: String,
    val type: ConfigurationType,
    var parameters: Map<String, String> = emptyMap()
)