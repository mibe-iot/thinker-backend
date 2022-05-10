package com.mibe.iot.thinker.app.messaging.email

import com.mibe.iot.thinker.service.messaging.email.EmailUseCase
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val javaMailSender: JavaMailSender
): EmailUseCase {

    override fun sendEmail() {
        val message = SimpleMailMessage().apply {
            setFrom("noreply@thinker.com")
            setTo("ilboogl@gmail.com")
            setSubject("test")
            setText("Ho-ho-ho")
        }
        javaMailSender.send(message)
    }
}