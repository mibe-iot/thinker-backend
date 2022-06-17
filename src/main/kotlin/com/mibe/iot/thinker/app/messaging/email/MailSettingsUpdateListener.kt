package com.mibe.iot.thinker.app.messaging.email

import com.mibe.iot.thinker.domain.settings.MailSettings
import com.mibe.iot.thinker.domain.settings.SettingsType
import com.mibe.iot.thinker.service.settings.AppSettingsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.springframework.context.ApplicationListener
import org.springframework.context.ApplicationStartupAware
import org.springframework.core.metrics.ApplicationStartup
import org.springframework.stereotype.Component
import java.util.concurrent.Executors

@Component
class MailSettingsUpdateListener(
    private val configurableMailSender: ConfigurableMailSender,
    private val appSettingsUseCase: AppSettingsUseCase
) : ApplicationStartupAware, ApplicationListener<EmailSettingsUpdatedEvent> {
    private val log = KotlinLogging.logger {}

    private val updateSettingsScope = CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())

    override fun setApplicationStartup(applicationStartup: ApplicationStartup) {
        updateMailSenderSettings()
    }

    override fun onApplicationEvent(event: EmailSettingsUpdatedEvent) {
        updateMailSenderSettings()
    }

    private fun updateMailSenderSettings() {
        updateSettingsScope.launch {
            log.info { "Trying to setup email sender with settings from db" }
            if (appSettingsUseCase.settingsWithTypeExist(SettingsType.MAIL)) {
                log.info { "Found mail settings  for sender" }
                configurableMailSender.updateSettings(appSettingsUseCase.getSettings(SettingsType.MAIL) as MailSettings)
                log.info { "Update sender config successfully" }
            }
        }
    }
}