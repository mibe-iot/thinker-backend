package com.mibe.iot.thinker.device.adapter.to.web

import com.mibe.iot.thinker.device.application.port.to.RegisterDeviceUseCase
import com.mibe.iot.thinker.device.domain.Device
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/devices")
class DeviceController
@Autowired constructor(
    private val registerDeviceUseCase: RegisterDeviceUseCase
) {

    @PostMapping("")
    fun saveDevice(@RequestBody device: Mono<Device>): Mono<Device> {
        return registerDeviceUseCase.registerDevice(device)
    }
}
