package com.mibe.iot.thinker.app.hooks.to.web

import com.mibe.iot.thinker.app.hooks.to.web.model.DeviceHooksAndReportTypesModel
import com.mibe.iot.thinker.domain.hooks.Hook
import com.mibe.iot.thinker.domain.hooks.Trigger
import com.mibe.iot.thinker.service.hooks.HookExecutorUseCase
import com.mibe.iot.thinker.service.hooks.HookUseCase
import com.mibe.iot.thinker.service.messaging.email.EmailUseCase
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

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

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun executeHook(@PathVariable id: String) {
        emailUseCase.sendEmail()
        hookExecutorUseCase.executeHookById(id)
    }

}