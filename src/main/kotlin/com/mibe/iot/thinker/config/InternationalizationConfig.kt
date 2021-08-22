package com.mibe.iot.thinker.config

import com.mibe.iot.thinker.message.application.HttpHeaderLocaleResolver
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.web.server.i18n.LocaleContextResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class InternationalizationConfig : WebMvcConfigurer {

    /**
     * HttpHeaderLocaleResolver resolves locale from HTTP request header
     */
    @Bean
    fun localeResolver(): LocaleContextResolver = HttpHeaderLocaleResolver()

    /**
     * Creates bean of message source
     */
    @Bean(name = ["messageSource"])
    fun messageSource(
        @Value("\${messages.folderPath}") folderPath: String,
        @Value("\${messages.file.common}") bundleName: String,
        @Value("\${messages.encoding}") encoding: String
    ): MessageSource =
        ReloadableResourceBundleMessageSource().apply {
            setBasename("$folderPath/$bundleName")
            setDefaultEncoding(encoding)
        }

    /**
     * Creates bean of error message source
     */
    @Bean(name = ["errorMessageSource"])
    fun errorMessageSource(
        @Value("\${messages.folderPath}") folderPath: String,
        @Value("\${messages.file.error}") bundleName: String,
        @Value("\${messages.encoding}") encoding: String
    ): MessageSource =
        ReloadableResourceBundleMessageSource().apply {
            setBasename("$folderPath/$bundleName")
            setDefaultEncoding(encoding)
        }

    /**
     * Configures message source for validation error messages
     */
    @Bean
    fun localValidatorFactoryBean(messageSource: MessageSource): LocalValidatorFactoryBean =
        LocalValidatorFactoryBean().apply { setValidationMessageSource(messageSource) }
}
