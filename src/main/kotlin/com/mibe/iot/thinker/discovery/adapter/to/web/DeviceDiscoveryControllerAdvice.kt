package com.mibe.iot.thinker.discovery.adapter.to.web

import com.mibe.iot.thinker.discovery.application.exception.DiscoveredDeviceNotFoundException
import com.mibe.iot.thinker.message.application.MessageService
import com.mibe.iot.thinker.web.error.ErrorData
import com.mibe.iot.thinker.web.error.toErrorData
import java.util.Locale
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
        return exception.toErrorData(
            messageService.getErrorMessage(DISCOVERED_DEVICE_NOT_FOUND, locale, exception.address),
            DISCOVERED_DEVICE_NOT_FOUND,
            HttpStatus.NOT_FOUND
        )
    }

    companion object {
        const val DISCOVERED_DEVICE_NOT_FOUND = "discovery.device.not.found"
    }

}