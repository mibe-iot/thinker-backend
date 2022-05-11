package com.mibe.iot.thinker.service.messaging.email

import com.mibe.iot.thinker.domain.settings.MailSettings

interface ConfigurableMailSenderUseCase {

    fun updateSettings(mailSettings: MailSettings)

}