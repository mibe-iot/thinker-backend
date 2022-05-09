package com.mibe.iot.thinker.service.hooks.port

import com.mibe.iot.thinker.domain.hooks.Hook
import kotlinx.coroutines.flow.Flow

interface HookPort {

    suspend fun getHookById(id: String): Hook?

    suspend fun getAllHooks(): Flow<Hook>

    suspend fun createHook(hook: Hook)

}