package com.mibe.iot.thinker.app.settings.to.web

import com.mibe.iot.thinker.app.message.MessageService
import com.mibe.iot.thinker.app.settings.exception.AppSettingsNotFoundException
import com.mibe.iot.thinker.app.settings.exception.InvalidAppSettingsException
import com.mibe.iot.thinker.app.validation.domain.ValidationErrorModel
import com.mibe.iot.thinker.app.web.ErrorData
import com.mibe.iot.thinker.domain.settings.AppSettings
import com.mibe.iot.thinker.service.discovery.ControlDeviceDiscoveryUseCase
import com.mibe.iot.thinker.service.settings.AppSettingsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("api/settings")
class AppSettingsController(
    private val appSettingsUseCase: AppSettingsUseCase,
    private val controlDeviceDiscoveryUseCase: ControlDeviceDiscoveryUseCase,
    private val messageService: MessageService
) {
    private val log = KotlinLogging.logger {}

    @PostMapping("")
    suspend fun sendSettings(@RequestBody appSettings: AppSettings) {
        log.info { "Update application settings" }
        appSettingsUseCase.updateSettings(appSettings)
        log.info { "Application settings updated have been updated successfully" }

        // Connection data depends on application settings
        if (controlDeviceDiscoveryUseCase.isDiscovering()) {
            controlDeviceDiscoveryUseCase.refreshDeviceConnectionData()
        }
    }

    @GetMapping("")
    suspend fun getSettings(): AppSettings {
        log.info { "Obtaining application settings" }
        return appSettingsUseCase.getAppSettings() ?: throw AppSettingsNotFoundException()
    }

    @ExceptionHandler(AppSettingsNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleAppSettingsNotFound(locale: Locale): ErrorData {
        log.error { "Application settings not found" }
        return ErrorData(
            description = messageService.getErrorMessage(APP_SETTINGS_NOT_FOUND, locale),
            descriptionKey = APP_SETTINGS_NOT_FOUND,
            httpStatus = HttpStatus.NOT_FOUND.value()
        )
    }

    @ExceptionHandler(InvalidAppSettingsException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleInvalidAppSettings(exception: InvalidAppSettingsException, locale: Locale): Flow<ValidationErrorModel> {
        val errorData = exception.errorDescriptions.asFlow()
            .map { errorPair ->
                ValidationErrorModel(
                    errorPair.second,
                    messageService.getTemplatedMessage(errorPair.first, locale)
                )
            }
        log.error { "Validation error: ${exception}; ErrorData: $errorData" }
        return errorData
    }

    companion object {
        const val APP_SETTINGS_NOT_FOUND = "settings.app.not.found"
    }

}