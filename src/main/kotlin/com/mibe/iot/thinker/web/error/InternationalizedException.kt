package com.mibe.iot.thinker.web.error

open class InternationalizedException(
    val messageKey: String,
    val logMessage: String? = null,
    vararg messagesArguments: Any
) : Exception(messageKey)
