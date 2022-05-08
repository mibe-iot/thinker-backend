package com.mibe.iot.thinker.app.hooks.to.web

import com.mibe.iot.thinker.service.hooks.HookExecutorUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/hooks")
class HooksController(
    private val hookExecutorUseCase: HookExecutorUseCase
) {

    @GetMapping("")
    fun execute() {
        hookExecutorUseCase.executeHookById("1")
    }

}