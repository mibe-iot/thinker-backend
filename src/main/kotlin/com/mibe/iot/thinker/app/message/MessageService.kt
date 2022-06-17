package com.mibe.iot.thinker.app.message

import com.mibe.iot.thinker.service.resource.GetErrorMessageUseCase
import com.mibe.iot.thinker.service.resource.GetMessageUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.*

/**
 * Implements use case of getting message by it's key (code)
 */
@Service
class MessageService
@Autowired constructor(
    @Qualifier("messageSource") private val messageSource: MessageSource,
    @Qualifier("errorMessageSource") private val errorMessageSource: MessageSource
) : GetMessageUseCase, GetErrorMessageUseCase {

    override fun getMessage(messageKey: String, locale: Locale, vararg messageParameters: Any): String {
        return messageSource.getMessage(messageKey, messageParameters, locale)
    }

    override fun getErrorMessage(messageKey: String, locale: Locale, vararg messageParameters: Any): String {
        return errorMessageSource.getMessage(messageKey, messageParameters, locale)
    }

    override fun getErrorMessageOrDefault(
        messageKey: String,
        defaultMessage: String,
        locale: Locale,
        vararg messageParameters: Any
    ): String {
        return errorMessageSource.getMessage(messageKey, messageParameters, defaultMessage, locale)!!
    }

    override fun getTemplatedMessage(
        templatedMessage: String,
        locale: Locale
    ): String {
        val messageAndParameters = templatedMessage.split("|||")
        val messageKey = messageAndParameters[0]
        val messageParameters = messageAndParameters.drop(1)
        return getErrorMessageOrDefault(
            messageKey,
            messageKey,
            locale,
            *messageParameters.toTypedArray()
        )
    }
}
