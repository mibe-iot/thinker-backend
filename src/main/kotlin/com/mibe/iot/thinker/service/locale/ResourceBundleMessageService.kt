package com.mibe.iot.thinker.service.locale

import java.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service

@Service
class ResourceBundleMessageService
@Autowired constructor(
	private val messageSource: MessageSource
) : MessageService {

	override fun getMessage(messageKey: String, locale: Locale, vararg messageParameters: Any): String {
		return messageSource.getMessage(messageKey, messageParameters, locale)
	}

}