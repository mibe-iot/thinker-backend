package com.mibe.iot.thinker.app.device.to.web

import com.mibe.iot.thinker.app.device.to.web.dto.DeviceDto
import com.mibe.iot.thinker.app.device.to.web.dto.toDeviceUpdates
import com.mibe.iot.thinker.app.web.model.DeviceModel
import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.service.device.ControlDeviceActionUseCase
import com.mibe.iot.thinker.service.device.DeleteDeviceUseCase
import com.mibe.iot.thinker.service.device.GetDeviceUseCase
import com.mibe.iot.thinker.service.device.UpdateDeviceUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/devices")
class DeviceController
@Autowired constructor(
    private val updateDeviceUseCase: UpdateDeviceUseCase,
    private val getDeviceUseCase: GetDeviceUseCase,

    private val deleteDeviceUseCase: DeleteDeviceUseCase,
    private val controlDeviceActionUseCase: ControlDeviceActionUseCase
) {
    private val log = KotlinLogging.logger {}

    @GetMapping("", produces = [MediaType.APPLICATION_NDJSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    suspend fun getAllDevicesWithLatestReport(): Flow<DeviceModel> {
        val devicesWithReports = getDeviceUseCase.getAllDevicesWithLatestReports()
        return devicesWithReports.map { DeviceModel.from(it) }
    }

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
    ) {
        val updates = deviceDto.toDeviceUpdates()
        updateDeviceUseCase.updateDevicePartially(deviceId, updates)
    }

    @PostMapping("/{id}/{actionName}")
    suspend fun invokeDeviceAction(
        @PathVariable(name = "id") deviceId: String,
        @PathVariable(name = "actionName") actionName: String
    ) {
        controlDeviceActionUseCase.invokeAction(deviceId, actionName)
    }
}

