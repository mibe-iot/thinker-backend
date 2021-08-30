package com.mibe.iot.thinker.device.adapter.to.web

import com.mibe.iot.thinker.device.adapter.to.web.dto.DeviceDto
import com.mibe.iot.thinker.device.adapter.to.web.dto.RegistrationResultDto
import com.mibe.iot.thinker.device.adapter.to.web.dto.toDeviceUpdates
import com.mibe.iot.thinker.device.adapter.to.web.exception.CantResolveIpAddressException
import com.mibe.iot.thinker.device.application.port.to.RegisterDeviceUseCase
import com.mibe.iot.thinker.device.application.port.to.UpdateDeviceUseCase
import com.mibe.iot.thinker.device.domain.Device
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("/api/devices")
class DeviceRegistrationController
@Autowired constructor(
    private val registerDeviceUseCase: RegisterDeviceUseCase,
    private val updateDeviceUseCase: UpdateDeviceUseCase
) {

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun preRegisterDevice(@RequestBody device: Mono<Device>) = registerDeviceUseCase.registerDevice(device)

    @PostMapping("/{id}")
    fun finishRegistration(
        @PathVariable id: String,
        @RequestBody deviceDto: Mono<DeviceDto>,
        exchange: ServerWebExchange
    ): Mono<RegistrationResultDto> {
        return deviceDto.flatMap { dto ->
            val clientIp = exchange.request.remoteAddress?.address?.hostAddress ?: throw CantResolveIpAddressException()
            dto.apply {
                this.id = id
                this.ip = clientIp
            }.toDeviceUpdates().toMono()
        }.log().flatMap { updates ->
            updateDeviceUseCase.updateDevice(updates.toMono())
        }.flatMap { device ->
            RegistrationResultDto(device).toMono()
        }
    }
}
