package com.mibe.iot.thinker.web.error

import org.springframework.http.HttpStatus

fun Exception.toErrorData(description: String, descriptionKey: String, httpStatus: HttpStatus) = ErrorData(
    description,
    descriptionKey,
    message,
    httpStatus.value(),
    javaClass.simpleName
)
