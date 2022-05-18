package com.mibe.iot.thinker.service.hooks

import com.mibe.iot.thinker.domain.hooks.Hook
import com.mibe.iot.thinker.domain.hooks.Trigger
import kotlinx.coroutines.flow.Flow

interface HookUseCase {

    suspend fun getHooks(): Flow<Hook>

    suspend fun getHookById(id: String): Hook

    suspend fun createTriggersIfNotExist(deviceId: String, hookIds: List<String>, reportTypes: List<String>): Flow<Trigger>

    suspend fun deleteHook(hookId: String)

}