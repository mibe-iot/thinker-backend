package com.mibe.iot.thinker.app.web

import com.mibe.iot.thinker.app.message.MessageService
import com.mibe.iot.thinker.app.validation.domain.ValidationErrorModel
import com.mibe.iot.thinker.app.validation.domain.ValidationException
import com.mibe.iot.thinker.domain.exception.InternationalizedException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import mu.KotlinLogging
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

    private val log = KotlinLogging.logger {}

    /**
     * Handles ValidationException and returns internationalized message to user
     *
     * @param ex ValidationException
     * @param locale Locale resolved by LocaleResolver
     */
    @Order(Ordered.LOWEST_PRECEDENCE)
    @ExceptionHandler(InternationalizedException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInternationalizedException(ex: InternationalizedException, locale: Locale): ErrorData {
        val description = messageService.getErrorMessage(ex.messageKey, locale)
        log.error { "Internationalized exception: ${ex.messageKey} locale: ${locale.country}" }
        return ErrorData(
            description = description,
            descriptionKey = ex.messageKey,
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value()
        )
    }

    /**
     * Handles ValidationException and returns internationalized message to user
     *
     * @param ex ValidationException
     * @param locale Locale resolved by LocaleResolver
     */
    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    suspend fun handleValidationError(ex: ValidationException, locale: Locale): Flow<ValidationErrorModel> {
        val errorData = ex.errors.asFlow()
            .map { error ->
                ValidationErrorModel(
                    error.dataPath,
                    messageService.getTemplatedMessage(error.message, locale)
                )
            }
        log.error { "Validation error: ${ex.errors}; ErrorData: $errorData" }
        return errorData
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
        log.error(exception) { "Unhandled exception: ${exception.message}" }
        return ErrorData(
            description = messageService.getErrorMessage(UNHANDLED_EXCEPTION, locale),
            descriptionKey = UNHANDLED_EXCEPTION,
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value()
        )
    }
}
