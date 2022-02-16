package com.mibe.iot.thinker.message.application.port.to

import java.util.Locale

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
}
