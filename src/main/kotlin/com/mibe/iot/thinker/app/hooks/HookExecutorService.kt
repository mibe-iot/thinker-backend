package com.mibe.iot.thinker.app.hooks

import com.mibe.iot.thinker.domain.device.DeviceReport
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

    override suspend fun executeHookById(hookId: String, report: DeviceReport) {
        val hook = hookPort.getHookById(hookId)
            ?: throw HookNotFoundException(hookId)
        val executor = hookExecutors.firstOrNull { it.hookType == hook::class }
            ?: throw MatchingExecutorNotFoundException(hook::class)

        executor.executeHook(hook, report)
    }
}