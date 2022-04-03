package com.mibe.iot.thinker.app.discovery.to.web

import com.mibe.iot.thinker.app.discovery.validateAddress
import com.mibe.iot.thinker.app.validation.domain.ValidationException
import com.mibe.iot.thinker.app.web.model.DiscoveryStatusModel
import com.mibe.iot.thinker.domain.discovery.DiscoveredDevice
import com.mibe.iot.thinker.service.discovery.ConnectDiscoveredDeviceUseCase
import com.mibe.iot.thinker.service.discovery.ControlDeviceDiscoveryUseCase
import com.mibe.iot.thinker.service.discovery.GetDiscoveredDeviceUseCase
import com.mibe.iot.thinker.validation.application.throwOnInvalid
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/discovery")
class DeviceDiscoveryController
@Autowired constructor(
    private val controlDiscoveryUseCase: ControlDeviceDiscoveryUseCase,
    private val connectDiscoveredDeviceUseCase: ConnectDiscoveredDeviceUseCase,
    private val getDeviceDiscoveryUseCase: GetDiscoveredDeviceUseCase
) {

    @GetMapping("", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    suspend fun getDevices(): List<DiscoveredDevice> {
        return getDeviceDiscoveryUseCase.getDiscoveredDevices().toList()
    }

    @PostMapping("/status", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    suspend fun setDiscoveryActive(@RequestParam(name = "setActive") isActivateDiscovery: Boolean): DiscoveryStatusModel {
        if (isActivateDiscovery) {
            controlDiscoveryUseCase.startDiscovery()
        } else {
            controlDiscoveryUseCase.stopDiscovery()
        }
        return DiscoveryStatusModel(controlDiscoveryUseCase.isDiscovering())
    }

    @GetMapping("/status", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getDiscoveryStatus(): DiscoveryStatusModel {
        return DiscoveryStatusModel(controlDiscoveryUseCase.isDiscovering())
    }

    @PostMapping("/restart", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    suspend fun restartDiscovery(): DiscoveryStatusModel {
        controlDiscoveryUseCase.restartDiscovery()
        return DiscoveryStatusModel(controlDiscoveryUseCase.isDiscovering())
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