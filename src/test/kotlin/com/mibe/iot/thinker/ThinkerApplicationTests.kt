package com.mibe.iot.thinker

import com.mibe.iot.thinker.service.locale.ResourceBundleMessageService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException

class ThinkerApplicationTests : FreeSpec({
    "with mocked message source" - {
        val messageSource = mockk<MessageSource>(relaxed = true)
        val service = ResourceBundleMessageService(messageSource)
        "service.getMessage" - {
            "when no message found" - {
                every { messageSource.getMessage(any(), any(), any()) } throws NoSuchMessageException("")
                "throws NoSuchMessageFound" - {
                    shouldThrow<NoSuchMessageException> { service.getMessage("any") }
                }
            }
        }
    }
})
