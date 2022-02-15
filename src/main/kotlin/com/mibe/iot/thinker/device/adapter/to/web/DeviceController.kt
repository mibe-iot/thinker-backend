package com.mibe.iot.thinker.device.adapter.to.web

//import com.mibe.iot.thinker.device.adapter.from.mqtt.TestPublisher
import com.mibe.iot.thinker.device.adapter.from.mqtt.TestPublisher
import com.mibe.iot.thinker.device.adapter.to.mqtt.MainMqttSubscriber
import com.mibe.iot.thinker.device.adapter.to.web.dto.DeviceDto
import com.mibe.iot.thinker.device.adapter.to.web.dto.toDeviceUpdates
import com.mibe.iot.thinker.device.application.port.to.DeleteDeviceUseCase
import com.mibe.iot.thinker.device.application.port.to.GetDeviceUseCase
import com.mibe.iot.thinker.device.application.port.to.UpdateDeviceUseCase
import com.mibe.iot.thinker.device.domain.Device
import kotlinx.coroutines.reactive.asFlow
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
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
    fun getAllDevices(exchange: ServerWebExchange) = getDeviceUseCase.getAllDevices().asFlow()

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getDevice(@PathVariable id: String) = getDeviceUseCase.getDevice(id)

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
        log.info{ "Sending mqtt"}
        testPublisher.publish()
    }
}
