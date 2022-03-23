package com.mibe.iot.thinker.app.message

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.web.reactive.config.DelegatingWebFluxConfiguration
import org.springframework.web.server.i18n.LocaleContextResolver

@Configuration
class InternationalizationConfig : DelegatingWebFluxConfiguration() {

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

    override fun localeContextResolver(): LocaleContextResolver = HttpHeaderLocaleResolver()
}
