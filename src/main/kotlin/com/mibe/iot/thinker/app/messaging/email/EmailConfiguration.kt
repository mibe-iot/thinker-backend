package com.mibe.iot.thinker.app.messaging.email

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class EmailConfiguration {

    @Bean
    fun javaMailSenderImpl(
        @Value("\${spring.mail.host}") host: String,
        @Value("\${spring.mail.port}") port: Int,
        @Value("\${spring.mail.properties.mail.smtp.auth}") auth: Boolean,
        @Value("\${spring.mail.properties.mail.smtp.starttls.enable}") startTlsEnabled: Boolean,
    ): JavaMailSenderImpl {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = host
        mailSender.port = port

        mailSender.javaMailProperties.apply {
            put("mail.transport.protocol", "smtp")
            put("mail.smtp.auth", auth)
            put("mail.smtp.starttls.enable", startTlsEnabled)
        }

        return mailSender
    }

}