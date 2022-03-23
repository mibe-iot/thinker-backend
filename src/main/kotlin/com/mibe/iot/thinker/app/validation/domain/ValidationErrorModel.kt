package com.mibe.iot.thinker.app.validation.domain

data class ValidationErrorModel(
    val dataPath: String,
    val message: String
)
