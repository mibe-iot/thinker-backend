package com.mibe.iot.thinker.message.application

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE
import org.springframework.web.server.ServerWebExchange
import java.util.*

class HttpHeaderLocaleResolverTest : FreeSpec({
    val resolver = HttpHeaderLocaleResolver()

    "resolveLocaleContext()" - {
        "with mocked exchange" - {
            val exchange = mockk<ServerWebExchange>()
            "when language tag passed in Http header" - {
                val langTag = Locale.US.toLanguageTag()
                every { exchange.request.headers.getFirst(ACCEPT_LANGUAGE) } returns langTag
                "must return LocaleContext with locale for given tag" - {
                    resolver.resolveLocaleContext(exchange).locale shouldBe Locale.forLanguageTag(langTag)
                }
            }
            "when wrong language tag passed in Http header" - {
                val langTag = "Some invalid lang tag"
                every { exchange.request.headers.getFirst(ACCEPT_LANGUAGE) } returns langTag
                "must return LocaleContext with default locale" - {
                    resolver.resolveLocaleContext(exchange).locale shouldBe Locale.forLanguageTag("")
                }
            }
            "when ACCEPT_LANGUAGE header is null" - {
                every { exchange.request.headers.getFirst(ACCEPT_LANGUAGE) } returns null
                "must return default locale" - {
                    resolver.resolveLocaleContext(exchange).locale shouldBe Locale.getDefault()
                }
            }
            "when ACCEPT_LANGUAGE header is empty" - {
                every { exchange.request.headers.getFirst(ACCEPT_LANGUAGE) } returns ""
                "must return default locale" - {
                    resolver.resolveLocaleContext(exchange).locale shouldBe Locale.getDefault()
                }
            }
        }
    }
    "setLocaleContext" - {
        "in any case" - {
            val exchange = mockk<ServerWebExchange>(relaxed = true)
            "should throw UnsupportedOperationException" - {
                shouldThrow<UnsupportedOperationException> { resolver.setLocaleContext(exchange, null) }
            }
        }
    }
})
