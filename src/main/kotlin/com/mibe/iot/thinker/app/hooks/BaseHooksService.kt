package com.mibe.iot.thinker.app.hooks

import com.mibe.iot.thinker.app.validation.validateEmailAddress
import com.mibe.iot.thinker.domain.hooks.SendEmailHook
import com.mibe.iot.thinker.service.hooks.BaseHooksUseCase
import com.mibe.iot.thinker.service.hooks.port.HookPort
import com.mibe.iot.thinker.validation.application.throwOnInvalid
import org.springframework.stereotype.Service

@Service
class BaseHooksService(
    private val hookPort: HookPort
): BaseHooksUseCase {

    override suspend fun createSendEmailHook(sendEmailHook: SendEmailHook) {
        validateEmailAddress(sendEmailHook.emailAddress.address).throwOnInvalid()
        hookPort.createHook(sendEmailHook)
    }
}