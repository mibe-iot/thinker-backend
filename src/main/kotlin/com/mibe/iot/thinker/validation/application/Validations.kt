package com.mibe.iot.thinker.validation.application

import com.mibe.iot.thinker.validation.domain.ValidationException
import io.konform.validation.Invalid
import io.konform.validation.Validation
import io.konform.validation.ValidationResult
import reactor.core.publisher.Mono

/**
 * Validates valueUnderValidation and returns it as Mono if validation is successful or returns Mono.error if validation
 * has failed
 */
fun <T> mapToErrorMonoIfInvalid(valueUnderValidation: T, validation: Validation<T>): Mono<T> {
    val validationResult = validation.validate(valueUnderValidation)
    return if (validationResult is Invalid<*>) {
        Mono.error(ValidationException(validationResult.errors))
    } else {
        Mono.just(valueUnderValidation!!)
    }
}

fun <T> ValidationResult<T>.throwOnInvalid() = run {
    if (this is Invalid<*>) throw ValidationException(this.errors)
}