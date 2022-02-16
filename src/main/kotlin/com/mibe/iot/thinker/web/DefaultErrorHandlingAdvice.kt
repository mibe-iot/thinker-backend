package com.mibe.iot.thinker.web

import com.mibe.iot.thinker.message.application.MessageService
import com.mibe.iot.thinker.validation.adapter.web.model.ValidationErrorModel
import com.mibe.iot.thinker.validation.domain.ValidationException
import com.mibe.iot.thinker.web.error.ErrorData
import com.mibe.iot.thinker.web.error.InternationalizedException
import com.mibe.iot.thinker.web.error.UNHANDLED_EXCEPTION
import com.mibe.iot.thinker.web.error.toErrorData
import java.util.Locale
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * Handles exceptions that are thrown from controllers
 */
@RestControllerAdvice
class DefaultErrorHandlingAdvice
@Autowired constructor(
    private val messageService: MessageService
) {

    private val logger = KotlinLogging.logger { }

    /**
     * Handles ValidationException and returns internationalized message to user
     * @param ex ValidationException
     * @param locale Locale resolved by LocaleResolver
     */
    @Order(Ordered.LOWEST_PRECEDENCE)
    @ExceptionHandler(InternationalizedException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInternationalizedException(ex: InternationalizedException, locale: Locale): ErrorData {
        val message = messageService.getErrorMessage(ex.messageKey, locale)
        logger.error(ex) { message }
        return ex.toErrorData(
            message,
            ex.messageKey,
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    /**
     * Handles ValidationException and returns internationalized message to user
     * @param ex ValidationException
     * @param locale Locale resolved by LocaleResolver
     */
    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationError(ex: ValidationException, locale: Locale): List<ValidationErrorModel> {
        logger.error(ex) { "Validation error: $ex" }
        return ex.errors.map { error ->
            val messageAndParameters = error.message.split("|||")
            val messageKey = messageAndParameters[0]
            val messageParameters = messageAndParameters.drop(1)
            val message = messageService.getErrorMessageOrDefault(
                messageKey,
                messageKey,
                locale,
                *messageParameters.toTypedArray()
            )
            ValidationErrorModel(
                error.dataPath,
                message
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
        return exception.toErrorData(
            messageService.getErrorMessage(UNHANDLED_EXCEPTION, locale),
            UNHANDLED_EXCEPTION,
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}
