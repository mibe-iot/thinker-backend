package com.mibe.iot.thinker

import com.mibe.iot.thinker.app.message.MessageService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import java.lang.IllegalStateException
import java.util.*

class MessageServiceTest : FreeSpec({
    "with mocked message and error message sources" - {
        val messageSource = mockk<MessageSource>(relaxed = true)
        val errorMessageSource = mockk<MessageSource>(relaxed = true)
        val service = MessageService(messageSource, errorMessageSource)

        "service.getMessage" - {
            "when arguments are correct" - {
                val message = "message"
                val key = "messageKey"
                val locale = Locale.ENGLISH
                "and message is localized to current locale" - {
                    every { messageSource.getMessage(key, any(), locale) } returns message
                    "should return message" - {
                        service.getMessage(key, locale) shouldBe message
                    }
                }
                "but message is not localized to current locale" - {
                    every { messageSource.getMessage(key, any(), locale) } returns message
                    "should return message in default locale" - {
                        service.getMessage(key, locale) shouldBe message
                    }
                }
            }
            "when no message found" - {
                "throws any exception from MessageSource" - {
                    "like NoSuchMessageException" - {
                        every { messageSource.getMessage(any(), any(), any()) } throws NoSuchMessageException("")
                        shouldThrow<NoSuchMessageException> { service.getMessage("any") }
                    }
                    "like IllegalStateException" - {
                        every { messageSource.getMessage(any(), any(), any()) } throws IllegalStateException("")
                        shouldThrow<IllegalStateException> { service.getMessage("any") }
                    }
                }
            }
        }

        "service.getErrorMessage" - {
            "when arguments are correct" - {
                val message = "message"
                val key = "messageKey"
                val locale = Locale.ENGLISH
                "and message is localized to current locale" - {
                    every { errorMessageSource.getMessage(key, any(), locale) } returns message
                    "should return message" - {
                        service.getErrorMessage(key, locale) shouldBe message
                    }
                }
                "but message is not localized to current locale" - {
                    every { errorMessageSource.getMessage(key, any(), locale) } returns message
                    "should return message in default locale" - {
                        service.getErrorMessage(key, locale) shouldBe message
                    }
                }
            }
            "when no message found" - {
                "throws any exception from MessageSource" - {
                    "like NoSuchMessageException" - {
                        every { errorMessageSource.getMessage(any(), any(), any()) } throws NoSuchMessageException("")
                        shouldThrow<NoSuchMessageException> { service.getErrorMessage("any") }
                    }
                    "like IllegalStateException" - {
                        every { errorMessageSource.getMessage(any(), any(), any()) } throws IllegalStateException("")
                        shouldThrow<IllegalStateException> { service.getErrorMessage("any") }
                    }
                }
            }
        }
    }
})
