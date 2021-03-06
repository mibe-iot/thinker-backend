package com.mibe.iot.thinker.domain.exception

open class InternationalizedException(
    val messageKey: String,
    val logMessage: String? = null,
) : Exception(messageKey)
