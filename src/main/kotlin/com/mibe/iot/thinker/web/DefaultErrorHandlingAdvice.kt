package com.mibe.iot.thinker.web

import com.mibe.iot.thinker.message.application.MessageService
import com.mibe.iot.thinker.validation.adapter.web.model.ValidationErrorModel
import com.mibe.iot.thinker.validation.domain.ValidationException
import com.mibe.iot.thinker.web.error.ErrorData
import com.mibe.iot.thinker.web.error.UNHANDLED_EXCEPTION
import com.mibe.iot.thinker.web.error.mapExceptionToErrorData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

/**
 * Handles exceptions that are thrown from controllers
 */
@RestControllerAdvice
class DefaultErrorHandlingAdvice
@Autowired constructor(
    private val messageService: MessageService
) {

    /**
     * Handles ValidationException and returns internationalized message to user
     * @param ex ValidationException
     * @param locale Locale resolved by LocaleResolver
     */
    @ExceptionHandler(ValidationException::class)
    fun handleValidationError(ex: ValidationException, locale: Locale): List<ValidationErrorModel> {
        return ex.errors.map { error ->
            val messageAndParameters = error.message.split("|||")
            val messageKey = messageAndParameters[0]
            val messageParameters = messageAndParameters.drop(1)
            ValidationErrorModel(
                error.dataPath,
                messageService.getErrorMessageOrDefault(
                    messageKey, messageKey, locale = locale, *messageParameters.toTypedArray()
                )

            )
        }
    }

    /**
     * Handles any exception, collects info about it and returns internationalized message to user
     *
     * @param exception any Exception subclass
     * @param locale Locale resolved by LocaleResolver
     */
    @ExceptionHandler(Exception::class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun defaultExceptionHandler(exception: Exception, locale: Locale): ErrorData {
        return exception.mapExceptionToErrorData(
            messageService.getErrorMessage(UNHANDLED_EXCEPTION, locale),
            UNHANDLED_EXCEPTION,
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}
