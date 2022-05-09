package com.mibe.iot.thinker.service.hooks

interface HookExecutorUseCase {

    suspend fun executeHookById(hookId: String)

}