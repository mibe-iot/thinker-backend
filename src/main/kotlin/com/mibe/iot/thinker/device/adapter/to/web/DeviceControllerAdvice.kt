package com.mibe.iot.thinker.device.adapter.to.web

import com.mibe.iot.thinker.device.application.port.to.exception.DeviceAlreadyExistsException
import com.mibe.iot.thinker.device.application.port.to.exception.DeviceNotFoundException
import com.mibe.iot.thinker.message.application.MessageService
import com.mibe.iot.thinker.web.error.ErrorData
import com.mibe.iot.thinker.web.error.toErrorData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

@RestControllerAdvice
class DeviceControllerAdvice @Autowired constructor(
    private val messageService: MessageService
) {

    @ExceptionHandler(DeviceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleDeviceNotFound(exception: DeviceNotFoundException, locale: Locale): ErrorData {
        return exception.toErrorData(
            messageService.getErrorMessage(DEVICE_NOT_FOUND, locale, exception.deviceId),
            DEVICE_NOT_FOUND,
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(DeviceAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleDeviceAlreadyExists(exception: DeviceAlreadyExistsException, locale: Locale): ErrorData {
        return exception.toErrorData(
            messageService.getErrorMessage(DEVICE_NAME_ALREADY_EXISTS, locale, exception.deviceName),
            DEVICE_NAME_ALREADY_EXISTS,
            HttpStatus.CONFLICT
        )
    }
}
