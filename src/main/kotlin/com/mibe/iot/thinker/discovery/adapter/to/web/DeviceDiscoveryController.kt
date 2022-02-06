package com.mibe.iot.thinker.discovery.adapter.to.web

import com.mibe.iot.thinker.discovery.application.port.to.ControlDeviceDiscoveryUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/discovery")
class DeviceDiscoveryController
@Autowired constructor(
    private val controlDeviceDiscoveryUseCase: ControlDeviceDiscoveryUseCase
) {

    @GetMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    fun startDiscovery(): String {
        controlDeviceDiscoveryUseCase.startDiscovery()
        return "Discovery started"
    }

    @GetMapping("/stop")
    @ResponseStatus(HttpStatus.OK)
    fun stopDiscovery(): String {
        controlDeviceDiscoveryUseCase.startDiscovery()
        return "Discovery stopped"
    }


}