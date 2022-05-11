package com.mibe.iot.thinker.app.messaging.email

import com.mibe.iot.thinker.domain.settings.MailSettings
import com.mibe.iot.thinker.service.messaging.email.ConfigurableMailSenderUseCase
import com.mibe.iot.thinker.service.settings.AppSettingsUseCase
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.stereotype.Component
import java.io.InputStream
import javax.mail.internet.MimeMessage

@Component
class ConfigurableMailSender(
    private val javaMailSenderImpl: JavaMailSenderImpl
): JavaMailSender, ConfigurableMailSenderUseCase {

    public var isConfigured: Boolean = false
    override fun updateSettings(mailSettings: MailSettings) {
        isConfigured = true
        javaMailSenderImpl.username = String(mailSettings.mailUsername)
        javaMailSenderImpl.password = String(mailSettings.mailPassword)
    }

    override fun send(mimeMessage: MimeMessage) {
        javaMailSenderImpl.send(mimeMessage)
    }

    override fun send(vararg mimeMessages: MimeMessage?) {
        javaMailSenderImpl.send(*mimeMessages)
    }

    override fun send(mimeMessagePreparator: MimeMessagePreparator) {
        javaMailSenderImpl.send(mimeMessagePreparator)
    }

    override fun send(vararg mimeMessagePreparators: MimeMessagePreparator?) {
        javaMailSenderImpl.send(*mimeMessagePreparators)
    }

    override fun send(simpleMessage: SimpleMailMessage) {
        javaMailSenderImpl.send(simpleMessage)
    }

    override fun send(vararg simpleMessages: SimpleMailMessage?) {
        javaMailSenderImpl.send(*simpleMessages)
    }

    override fun createMimeMessage(): MimeMessage {
        return javaMailSenderImpl.createMimeMessage()
    }

    override fun createMimeMessage(contentStream: InputStream): MimeMessage {
        return javaMailSenderImpl.createMimeMessage()
    }
}