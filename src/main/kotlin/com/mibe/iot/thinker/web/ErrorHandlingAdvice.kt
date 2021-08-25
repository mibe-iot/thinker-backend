package com.mibe.iot.thinker.web

import com.mibe.iot.thinker.message.application.MessageService
import com.mibe.iot.thinker.validation.adapter.web.model.ValidationErrorModel
import com.mibe.iot.thinker.validation.domain.ValidationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

/**
 * Handles exceptions that are thrown from controllers
 */
@RestControllerAdvice
class ErrorHandlingAdvice
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
            ValidationErrorModel(
                error.dataPath,
                messageService.getErrorMessageOrDefault(error.message, error.message, locale = locale)
            )
        }
    }
}
