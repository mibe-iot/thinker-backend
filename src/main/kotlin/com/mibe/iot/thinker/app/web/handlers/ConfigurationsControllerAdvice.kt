package com.mibe.iot.thinker.app.web.handlers

import com.mibe.iot.thinker.app.message.MessageService
import com.mibe.iot.thinker.app.web.ErrorData
import com.mibe.iot.thinker.domain.exception.WifiConfigurationNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

@RestControllerAdvice
class ConfigurationsControllerAdvice
@Autowired constructor(
    private val messageService: MessageService
) {

    @ExceptionHandler(WifiConfigurationNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleWifiConfigurationNotFound(exception: WifiConfigurationNotFoundException, locale: Locale): ErrorData {
        return ErrorData(
            description = messageService.getErrorMessage(WIFI_CONFIGURATION_NOT_FOUND, locale),
            descriptionKey = WIFI_CONFIGURATION_NOT_FOUND,
            httpStatus = HttpStatus.NOT_FOUND.value()
        )
    }

    companion object {
        const val WIFI_CONFIGURATION_NOT_FOUND = "configurations.wifi.not.found"
    }

}