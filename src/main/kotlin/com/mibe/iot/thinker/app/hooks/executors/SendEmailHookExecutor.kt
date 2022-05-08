package com.mibe.iot.thinker.app.hooks.executors

import com.mibe.iot.thinker.domain.hooks.Hook
import com.mibe.iot.thinker.domain.hooks.SendEmailHook
import com.mibe.iot.thinker.service.hooks.HookExecutor
import mu.KotlinLogging
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class SendEmailHookExecutor: HookExecutor {
    private val log = KotlinLogging.logger {}

    override val hookType: KClass<out Hook>
        get() = SendEmailHook::class

    override fun executeHook(hook: Hook) {
        if (hook is SendEmailHook) {
            log.info { "Executing hook $hook" }
        } else {
            log.error { "Unknown hook type" }
        }
    }
}