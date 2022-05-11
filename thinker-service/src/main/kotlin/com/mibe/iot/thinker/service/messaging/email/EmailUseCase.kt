package com.mibe.iot.thinker.service.messaging.email

import com.mibe.iot.thinker.domain.data.EmailAddress

interface EmailUseCase {

    fun sendEmail(to: EmailAddress, subject: String, body: String)

}