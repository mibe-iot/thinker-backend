package com.mibe.iot.thinker.service.hooks

import com.mibe.iot.thinker.domain.hooks.SendEmailHook

interface BaseHooksUseCase {

    suspend fun createSendEmailHook(sendEmailHook: SendEmailHook)

}