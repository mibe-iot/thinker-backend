package com.mibe.iot.thinker.validation

import com.mibe.iot.thinker.validation.domain.ValidationException
import io.konform.validation.Invalid
import io.konform.validation.Validation
import reactor.core.publisher.Mono

fun <T> mapToErrorMonoIfInvalid(valueUnderValidation: T, validation: Validation<T>): Mono<T> {
    val validationResult = validation.validate(valueUnderValidation)
    return if (validationResult is Invalid<*>) {
        Mono.error(ValidationException(validationResult.errors))
    } else {
        Mono.just(valueUnderValidation)
    }
}
