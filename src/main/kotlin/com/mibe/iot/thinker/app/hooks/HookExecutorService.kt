package com.mibe.iot.thinker.app.hooks

import com.mibe.iot.thinker.domain.data.EmailAddress
import com.mibe.iot.thinker.domain.hooks.Hook
import com.mibe.iot.thinker.domain.hooks.SendEmailHook
import com.mibe.iot.thinker.service.hooks.HookExecutor
import com.mibe.iot.thinker.service.hooks.HookExecutorUseCase
import org.springframework.stereotype.Service
import kotlin.reflect.cast
import kotlin.reflect.safeCast

@Service
class HookExecutorService(
    private val hookExecutors: List<HookExecutor>
): HookExecutorUseCase {

    override fun executeHookById(hookId: String) {
        val hook : Hook = SendEmailHook("1", "1", "1", EmailAddress("1"))
        val executor = hookExecutors.first { it.hookType == hook::class }
        executor.executeHook(hook)
    }
}