package com.mibe.iot.thinker.app.messaging.email

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

//@Configuration
//class EmailConfiguration {
//
//    @Bean
//    fun javaMailSender(
//        @Value("\${thinker.mail.host}") host: String,
//        @Value("\${thinker.mail.host}") port: Int,
//        @Value("\${thinker.mail.username}") username: String,
//        @Value("\${thinker.mail.password}") password: String,
//    ): JavaMailSenderImpl {
//        val mailSender = JavaMailSenderImpl()
//        mailSender.host = "smtp.gmail.com"
//        mailSender.port = 587
//
//        mailSender.username = "my.gmail@gmail.com"
//        mailSender.password = "password"
//
//        val props: Properties = mailSender.javaMailProperties.apply {
//            put("mail.transport.protocol", "smtp")
//            put("mail.smtp.auth", "true")
//            put("mail.smtp.starttls.enable", "true")
//            put("mail.debug", "true")
//        }
//
//        return mailSender
//    }
//
//}