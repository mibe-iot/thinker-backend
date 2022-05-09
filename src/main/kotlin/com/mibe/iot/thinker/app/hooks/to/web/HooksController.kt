package com.mibe.iot.thinker.app.hooks.to.web

import com.mibe.iot.thinker.domain.data.EmailAddress
import com.mibe.iot.thinker.domain.hooks.Hook
import com.mibe.iot.thinker.domain.hooks.SendEmailHook
import com.mibe.iot.thinker.service.hooks.HookExecutorUseCase
import com.mibe.iot.thinker.service.hooks.HookUseCase
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/hooks")
class HooksController(
    private val hookExecutorUseCase: HookExecutorUseCase,
    private val hookUseCase: HookUseCase
) {

    @GetMapping("")
    suspend fun getAll(): Flow<Hook> {
        return hookUseCase.getHooks()
    }
    @GetMapping("/{id}")
    fun execute(@PathVariable id: String): Hook {
        return SendEmailHook("1", "1", "1", EmailAddress("1"))
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun executeHook(@PathVariable id: String) {
        hookExecutorUseCase.executeHookById(id)
    }


}