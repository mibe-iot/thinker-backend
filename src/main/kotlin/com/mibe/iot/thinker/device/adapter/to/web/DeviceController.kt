package com.mibe.iot.thinker.device.adapter.to.web

import com.mibe.iot.thinker.device.adapter.to.web.dto.DeviceDto
import com.mibe.iot.thinker.device.adapter.to.web.dto.toDeviceUpdates
import com.mibe.iot.thinker.device.application.port.to.DeleteDeviceUseCase
import com.mibe.iot.thinker.device.application.port.to.GetDeviceUseCase
import com.mibe.iot.thinker.device.application.port.to.UpdateDeviceUseCase
import com.mibe.iot.thinker.device.domain.Device
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("/api/devices")
class DeviceController
@Autowired constructor(
    private val updateDeviceUseCase: UpdateDeviceUseCase,
    private val getDeviceUseCase: GetDeviceUseCase,
    private val deleteDeviceUseCase: DeleteDeviceUseCase
) {

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun getAllDevices() = getDeviceUseCase.getAllDevices()

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getDevice(@PathVariable id: String) = getDeviceUseCase.getDevice(id)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteDevice(@PathVariable id: String) = deleteDeviceUseCase.deleteDevice(id)

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateDevice(
        @PathVariable(name = "id") deviceId: String,
        @RequestBody deviceDto: Mono<DeviceDto>
    ): Mono<Device> {
        return deviceDto.flatMap { dto ->
            dto.apply { id = deviceId }.toDeviceUpdates().toMono()
        }.flatMap {
            updateDeviceUseCase.updateDevice(Mono.just(it))
        }
    }
}
