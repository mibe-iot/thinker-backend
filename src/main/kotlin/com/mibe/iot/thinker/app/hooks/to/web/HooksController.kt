package com.mibe.iot.thinker.app.hooks.to.web

import com.mibe.iot.thinker.domain.device.DeviceReport
import com.mibe.iot.thinker.domain.hooks.Hook
import com.mibe.iot.thinker.service.hooks.HookExecutorUseCase
import com.mibe.iot.thinker.service.hooks.HookUseCase
import com.mibe.iot.thinker.service.messaging.email.EmailUseCase
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/hooks")
class HooksController(
    private val hookExecutorUseCase: HookExecutorUseCase,
    private val hookUseCase: HookUseCase,
    private val emailUseCase: EmailUseCase
) {

    @GetMapping("")
    suspend fun getAll(): Flow<Hook> {
        return hookUseCase.getHooks()
    }

    @GetMapping("/{id}")
    suspend fun getById(@PathVariable id: String): Hook {
        return hookUseCase.getHookById(id)
    }

    @GetMapping("/exec/{id}")
    suspend fun exec(@PathVariable id: String) {
        hookExecutorUseCase.executeHookById(id, DeviceReport("123123", id, "Hello", mapOf("a" to "b")))
    }

    @DeleteMapping("/{id}")
    suspend fun deleteHook(@PathVariable id: String) {
        hookUseCase.deleteHook(id)
    }

}