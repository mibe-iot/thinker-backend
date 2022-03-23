package com.mibe.iot.thinker.app.discovery.to.web

import com.mibe.iot.thinker.service.discovery.ConnectDiscoveredDeviceUseCase
import com.mibe.iot.thinker.service.discovery.ControlDeviceDiscoveryUseCase
import com.mibe.iot.thinker.service.discovery.GetDiscoveredDeviceUseCase
import com.mibe.iot.thinker.domain.discovery.DiscoveredDevice
import com.mibe.iot.thinker.app.discovery.domain.validation.validateAddress
import com.mibe.iot.thinker.validation.application.throwOnInvalid
import com.mibe.iot.thinker.app.validation.domain.ValidationException
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/discovery")
class DeviceDiscoveryController
@Autowired constructor(
    private val controlDeviceDiscoveryUseCase: ControlDeviceDiscoveryUseCase,
    private val connectDiscoveredDeviceUseCase: ConnectDiscoveredDeviceUseCase,
    private val getDeviceDiscoveryUseCase: GetDiscoveredDeviceUseCase
) {

    @GetMapping("", produces = [MediaType.APPLICATION_NDJSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    suspend fun getDevices(): Flow<DiscoveredDevice> {
        return getDeviceDiscoveryUseCase.getDiscoveredDevices()
    }

    @GetMapping("/start", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun startDiscovery(): ResponseEntity<String> {
        controlDeviceDiscoveryUseCase.startDiscovery()
        return ResponseEntity.ok("Discovery started")
    }

    @GetMapping("/stop")
    @ResponseStatus(HttpStatus.OK)
    fun stopDiscovery(): String {
        controlDeviceDiscoveryUseCase.stopDiscovery()
        return "Discovery stopped"
    }

    /**
     * Connects device by MAC address.
     *
     * @throws [ValidationException] on invalid MAC address
     */
    @PostMapping("/connect/{address}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun connectDevice(@PathVariable(name = "address") address: String) {
        val macAddress = address.replace('-', ':')
        validateAddress(macAddress).throwOnInvalid()
        connectDiscoveredDeviceUseCase.connectDeviceByAddress(macAddress)
    }

}