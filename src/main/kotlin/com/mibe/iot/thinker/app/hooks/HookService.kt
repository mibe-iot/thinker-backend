package com.mibe.iot.thinker.app.hooks

import com.mibe.iot.thinker.domain.hooks.Hook
import com.mibe.iot.thinker.service.hooks.HookUseCase
import com.mibe.iot.thinker.service.hooks.port.HookPort
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class HookService(
    private val hookPort: HookPort
) : HookUseCase {

    override suspend fun getHooks(): Flow<Hook> {
        return hookPort.getAllHooks()
    }

    override suspend fun getHookById(): Hook {
        TODO("Not yet implemented")
    }
}