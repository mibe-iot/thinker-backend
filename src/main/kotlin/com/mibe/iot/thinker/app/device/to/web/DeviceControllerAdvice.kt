package com.mibe.iot.thinker.app.device.to.web

import com.mibe.iot.thinker.app.message.MessageService
import com.mibe.iot.thinker.app.web.ErrorData
import com.mibe.iot.thinker.service.device.exception.DeviceActionNotFoundException
import com.mibe.iot.thinker.service.device.exception.DeviceAlreadyExistsException
import com.mibe.iot.thinker.service.device.exception.DeviceNotFoundException
import java.util.Locale
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class DeviceControllerAdvice @Autowired constructor(
    private val messageService: MessageService
) {

    @ExceptionHandler(DeviceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleDeviceNotFound(exception: DeviceNotFoundException, locale: Locale): ErrorData {
        val descriptionKey = if (exception.deviceId != null) {
            DEVICE_NOT_FOUND_BY_ID
        } else {
            DEVICE_NOT_FOUND
        }
        val args = exception.deviceId?.let { listOf(it) } ?: emptyList()
        return ErrorData(
            description = messageService.getErrorMessage(descriptionKey, locale, args),
            descriptionKey = descriptionKey,
            httpStatus = HttpStatus.NOT_FOUND.value()
        )
    }

    @ExceptionHandler(DeviceActionNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleDeviceActionNotFound(exception: DeviceActionNotFoundException, locale: Locale): ErrorData {
        return ErrorData(
            description = messageService.getErrorMessage(
                DEVICE_ACTION_NOT_FOUND,
                locale,
                exception.deviceId ?: "[unknown]"
            ),
            descriptionKey = DEVICE_ACTION_NOT_FOUND,
            httpStatus = HttpStatus.NOT_FOUND.value()
        )
    }

    @ExceptionHandler(DeviceAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleDeviceAlreadyExists(exception: DeviceAlreadyExistsException, locale: Locale): ErrorData {
        return ErrorData(
            description = messageService.getErrorMessage(DEVICE_NAME_ALREADY_EXISTS, locale, exception.deviceName),
            descriptionKey = DEVICE_NAME_ALREADY_EXISTS,
            httpStatus = HttpStatus.CONFLICT.value()
        )
    }

    companion object {
        const val DEVICE_NOT_FOUND_BY_ID = "device.not.found.by.id"
        const val DEVICE_NOT_FOUND = "device.not.found"
        const val DEVICE_ACTION_NOT_FOUND = "device.action.not.found"
        const val DEVICE_NAME_ALREADY_EXISTS = "device.name.already.exists"
    }
}
