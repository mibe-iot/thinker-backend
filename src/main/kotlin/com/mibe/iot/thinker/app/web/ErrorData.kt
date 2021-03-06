package com.mibe.iot.thinker.app.web

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ErrorData(
    val httpStatus: Int = HttpStatus.INTERNAL_SERVER_ERROR.value(),
    val description: String,
    val descriptionKey: String = "",
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val timestamp: LocalDateTime = LocalDateTime.now()
)
