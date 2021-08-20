package com.mibe.iot.thinker.service.locale

import java.util.*

interface MessageService {

    fun getMessage(
        messageKey: String,
        locale: Locale = Locale.getDefault(),
        vararg messageParameters: Any = emptyArray()
    ): String
}
