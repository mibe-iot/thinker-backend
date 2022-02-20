package com.mibe.iot.thinker.device.adapter.to.web

import com.mibe.iot.thinker.device.adapter.from.mqtt.TestPublisher
import com.mibe.iot.thinker.device.adapter.to.web.dto.DeviceDto
import com.mibe.iot.thinker.device.adapter.to.web.dto.toDeviceUpdates
import com.mibe.iot.thinker.device.application.port.to.DeleteDeviceUseCase
import com.mibe.iot.thinker.device.application.port.to.GetDeviceUseCase
import com.mibe.iot.thinker.device.application.port.to.UpdateDeviceUseCase
import com.mibe.iot.thinker.device.application.port.to.exception.DeviceNotFoundException
import com.mibe.iot.thinker.domain.device.Device
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange

@RestController
@RequestMapping("/api/devices")
class DeviceController
@Autowired constructor(
    private val updateDeviceUseCase: UpdateDeviceUseCase,
    private val getDeviceUseCase: GetDeviceUseCase,
    private val deleteDeviceUseCase: DeleteDeviceUseCase,
    private val testPublisher: TestPublisher,
) {
    private val log = KotlinLogging.logger {}

    @GetMapping("", produces = [MediaType.APPLICATION_NDJSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getAllDevices() = getDeviceUseCase.getAllDevices()

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    suspend fun getDevice(@PathVariable id: String): Device {
        return getDeviceUseCase.getDevice(id)
            ?: throw DeviceNotFoundException(id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteDevice(@PathVariable id: String) = deleteDeviceUseCase.deleteDevice(id)

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun updateDevice(
        @PathVariable(name = "id") deviceId: String,
        @RequestBody deviceDto: DeviceDto,
        exchange: ServerWebExchange
    ): Device? {
        val updates = deviceDto.apply { id = deviceId }.toDeviceUpdates()
        return updateDeviceUseCase.updateDevice(updates)
    }

    @PostMapping("/mqtt")
    @ResponseStatus(HttpStatus.OK)
    fun postMqtt(@RequestParam(name = "temp") temp: Int) {
        log.info { "Sending mqtt" }
        testPublisher.publish()
    }
}

