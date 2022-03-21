package com.mibe.iot.thinker.app.discovery.to.web

import com.mibe.iot.thinker.app.discovery.exception.DiscoveredDeviceNotFoundException
import com.mibe.iot.thinker.app.message.MessageService
import com.mibe.iot.thinker.app.web.ErrorData
import java.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class DeviceDiscoveryControllerAdvice
@Autowired constructor(
    private val messageService: MessageService
) {

    @ExceptionHandler(DiscoveredDeviceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleDeviceNotFound(exception: DiscoveredDeviceNotFoundException, locale: Locale): ErrorData {
        return ErrorData(
            description = messageService.getErrorMessage(DISCOVERED_DEVICE_NOT_FOUND, locale, exception.address),
            descriptionKey = DISCOVERED_DEVICE_NOT_FOUND,
            httpStatus = HttpStatus.NOT_FOUND.value()
        )
    }

    companion object {
        const val DISCOVERED_DEVICE_NOT_FOUND = "discovery.device.not.found"
    }

}