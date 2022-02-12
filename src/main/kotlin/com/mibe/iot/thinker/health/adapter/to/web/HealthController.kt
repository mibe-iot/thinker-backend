package com.mibe.iot.thinker.health.adapter.to.web

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping(path = [
    "/api/health",
    "/"
])
class HealthController {

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun getHealth() = Mono.just("Server is running")

}