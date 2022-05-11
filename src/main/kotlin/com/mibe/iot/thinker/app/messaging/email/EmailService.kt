package com.mibe.iot.thinker.app.messaging.email

import com.mibe.iot.thinker.domain.data.EmailAddress
import com.mibe.iot.thinker.service.messaging.email.EmailUseCase
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val javaMailSender: JavaMailSender
): EmailUseCase {

    override fun sendEmail(to: EmailAddress, subject: String, body: String) {
        val message = SimpleMailMessage().apply {
            setTo(to.address)
            setSubject(subject)
            setText(body)
        }
        javaMailSender.send(message)
    }
}