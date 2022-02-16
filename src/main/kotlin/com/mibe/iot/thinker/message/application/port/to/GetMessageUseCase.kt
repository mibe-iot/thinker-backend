package com.mibe.iot.thinker.message.application.port.to

import java.util.Locale

interface GetMessageUseCase {

    fun getMessage(
        messageKey: String,
        locale: Locale = Locale.getDefault(),
        vararg messageParameters: Any = emptyArray()
    ): String
}
