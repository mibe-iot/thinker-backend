package com.mibe.iot.thinker.web.error

import org.springframework.http.HttpStatus

fun Exception.mapExceptionToErrorData(description: String, descriptionKey: String, httpStatus: HttpStatus) = ErrorData(
    description,
    descriptionKey,
    message,
    httpStatus.value(),
    javaClass.name
)
