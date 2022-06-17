package com.mibe.iot.thinker.app.messaging.email

import com.mibe.iot.thinker.domain.data.EmailAddress
import com.mibe.iot.thinker.service.messaging.email.EmailUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import java.util.concurrent.Executors

@Service
class EmailService(
    private val mailSender: ConfigurableMailSender
) : EmailUseCase {
    private val log = KotlinLogging.logger {}
    private val sendEmailScope = CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())

    override fun sendEmail(to: EmailAddress, subject: String, body: String) {
        if (mailSender.isConfigured) {
            sendEmailScope.launch {
                val message = SimpleMailMessage().apply {
                    setTo(to.address)
                    setFrom("noreply@thinker.com")
                    setSubject(subject)
                    setText(body)
                }
                mailSender.send(message)
            }
        } else {
            log.warn { "Mail settings are not configured" }
        }
    }
}