package com.mibe.iot.thinker.device.adapter.to.web

import com.mibe.iot.thinker.device.application.port.to.DeleteDeviceUseCase
import com.mibe.iot.thinker.device.application.port.to.GetDeviceUseCase
import com.mibe.iot.thinker.device.application.port.to.RegisterDeviceUseCase
import com.mibe.iot.thinker.device.domain.Device
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("/api/devices")
class DeviceController
@Autowired constructor(
    private val registerDeviceUseCase: RegisterDeviceUseCase,
    private val getDeviceUseCase: GetDeviceUseCase,
    private val deleteDeviceUseCase: DeleteDeviceUseCase
) {

    @PostMapping("")
    fun saveDevice(@RequestBody device: Mono<Device>): Mono<Device> {
        return registerDeviceUseCase.registerDevice(device)
    }

    @GetMapping("/{id}")
    fun getDevice(@PathVariable id: String): Mono<Device> {
        return getDeviceUseCase.getDevice(id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteDevice(@PathVariable id: String): Mono<Void> {
        return deleteDeviceUseCase.deleteDevice(id)
    }
}
