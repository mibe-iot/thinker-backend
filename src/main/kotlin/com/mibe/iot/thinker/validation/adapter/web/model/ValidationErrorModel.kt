package com.mibe.iot.thinker.validation.adapter.web.model

data class ValidationErrorModel(
    val dataPath: String,
    val message: String
)
