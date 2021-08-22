package com.mibe.iot.thinker.message.application

import org.springframework.context.i18n.LocaleContext
import org.springframework.context.i18n.SimpleLocaleContext
import org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.i18n.LocaleContextResolver
import java.util.*

class HttpHeaderLocaleResolver : LocaleContextResolver {

    override fun resolveLocaleContext(exchange: ServerWebExchange): LocaleContext {
        val language = exchange.request.headers.getFirst(ACCEPT_LANGUAGE)
        val targetLocale = if (!language.isNullOrEmpty()) {
            Locale.forLanguageTag(language)
        } else {
            Locale.getDefault()
        }
        return SimpleLocaleContext(targetLocale)
    }

    override fun setLocaleContext(exchange: ServerWebExchange, localeContext: LocaleContext?) {
        throw UnsupportedOperationException("Not Supported")
    }
}
