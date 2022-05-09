package com.mibe.iot.thinker.service.hooks

import com.mibe.iot.thinker.domain.hooks.Hook
import kotlinx.coroutines.flow.Flow

interface HookUseCase {

    suspend fun getHooks(): Flow<Hook>

    suspend fun getHookById(): Hook

}