package com.mibe.iot.thinker.device.adapter.to.web

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
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/devices")
class DeviceController
@Autowired constructor(
    private val updateDeviceUseCase: UpdateDeviceUseCase,
    private val getDeviceUseCase: GetDeviceUseCase,
    private val deleteDeviceUseCase: DeleteDeviceUseCase,
) {
    private val log = KotlinLogging.logger {}

    @GetMapping("", produces = [MediaType.APPLICATION_NDJSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getAllDevices() = getDeviceUseCase.getAllDevices()

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    suspend fun getDevice(@PathVariable(name = "id") id: String): Device {
        return getDeviceUseCase.getDevice(id)
    }

    @GetMapping("/find", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    suspend fun getDeviceByAddress(@RequestParam(name = "address") address: String): Device {
        val mappedAddress = address.replace("-", ":")
        return getDeviceUseCase.getDeviceByAddress(mappedAddress)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteDevice(@PathVariable(name = "id") id: String) {
        deleteDeviceUseCase.deleteDevice(id)
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun updateDevice(
        @PathVariable(name = "id") deviceId: String,
        @RequestBody deviceDto: DeviceDto
    ): Device? {
        val updates = deviceDto.apply { id = deviceId }.toDeviceUpdates()
        return updateDeviceUseCase.updateDevice(updates)
    }
}

