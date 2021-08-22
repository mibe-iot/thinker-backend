package com.mibe.iot.thinker.message.application.port.to

import java.util.*

interface GetErrorMessageUseCase {

    fun getErrorMessage(
        messageKey: String,
        locale: Locale = Locale.getDefault(),
        vararg messageParameters: Any = emptyArray()
    ): String
}
