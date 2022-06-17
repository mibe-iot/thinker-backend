package com.mibe.iot.thinker.app.hooks.to.web

import com.mibe.iot.thinker.domain.hooks.SendEmailHook
import com.mibe.iot.thinker.service.hooks.BaseHooksUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/hooks")
class BaseHooksController(
    private val baseHooksUseCase: BaseHooksUseCase
) {

    @PostMapping("/sendEmail")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createSendEmailHook(@RequestBody sendEmailHook: SendEmailHook) {
        baseHooksUseCase.createSendEmailHook(sendEmailHook)
    }

}