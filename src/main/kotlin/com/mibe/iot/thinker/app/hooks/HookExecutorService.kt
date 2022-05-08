package com.mibe.iot.thinker.app.hooks

import com.mibe.iot.thinker.service.hooks.HookExecutor
import com.mibe.iot.thinker.service.hooks.HookExecutorUseCase
import com.mibe.iot.thinker.service.hooks.exception.HookNotFoundException
import com.mibe.iot.thinker.service.hooks.exception.MatchingExecutorNotFoundException
import com.mibe.iot.thinker.service.hooks.port.HookPort
import org.springframework.stereotype.Service

@Service
class HookExecutorService(
    private val hookExecutors: List<HookExecutor>,
    private val hookPort: HookPort
) : HookExecutorUseCase {

    override fun executeHookById(hookId: String) {
        val hook = hookPort.getHookById(hookId)
            ?: throw HookNotFoundException(hookId)
        val executor = hookExecutors.firstOrNull { it.hookType == hook::class }
            ?: throw MatchingExecutorNotFoundException(hook::class)

        executor.executeHook(hook)
    }
}