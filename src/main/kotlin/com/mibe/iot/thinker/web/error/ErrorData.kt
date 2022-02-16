package com.mibe.iot.thinker.web.error

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import org.springframework.http.HttpStatus

data class ErrorData(
    val description: String,
    val descriptionKey: String = "",
    val message: String?,
    val httpStatus: Int = HttpStatus.INTERNAL_SERVER_ERROR.value(),
    val exceptionClass: String = "{Unknown}",
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val timestamp: LocalDateTime = LocalDateTime.now()
)
