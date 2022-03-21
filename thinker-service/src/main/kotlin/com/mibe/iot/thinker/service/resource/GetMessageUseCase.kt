package com.mibe.iot.thinker.service.resource

import java.util.*

interface GetMessageUseCase {

    fun getMessage(
        messageKey: String,
        locale: Locale = Locale.getDefault(),
        vararg messageParameters: Any = emptyArray()
    ): String
}
