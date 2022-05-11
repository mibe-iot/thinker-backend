package com.mibe.iot.thinker.service.resource

import java.util.*

interface GetErrorMessageUseCase {

    fun getErrorMessage(
        messageKey: String,
        locale: Locale = Locale.getDefault(),
        vararg messageParameters: Any = emptyArray()
    ): String

    fun getErrorMessageOrDefault(
        messageKey: String,
        defaultMessage: String,
        locale: Locale = Locale.getDefault(),
        vararg messageParameters: Any = emptyArray()
    ): String

    fun getTemplatedMessage(templatedMessage: String, locale: Locale): String
}
