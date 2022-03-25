package com.mibe.iot.thinker.app.configuration.to.web

import com.mibe.iot.thinker.domain.configuration.WifiConfiguration
import com.mibe.iot.thinker.service.configuration.WifiConfigurationUseCase
import com.mibe.iot.thinker.service.discovery.ControlDeviceDiscoveryUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/configurations")
class ConfigurationsController
@Autowired constructor(
    private val wifiConfigurationUseCase: WifiConfigurationUseCase,
    private val controlDeviceDiscoveryUseCase: ControlDeviceDiscoveryUseCase
) {

    @GetMapping("/wifi", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    suspend fun getWifiConfiguration() = wifiConfigurationUseCase.get()

    @RequestMapping(path = ["/wifi"], method = [RequestMethod.POST, RequestMethod.PUT])
    @ResponseStatus(HttpStatus.OK)
    suspend fun updateWifiConfig(@RequestBody wifiConfiguration: WifiConfiguration) {
        wifiConfigurationUseCase.update(wifiConfiguration)
        controlDeviceDiscoveryUseCase.refreshDeviceConnectionData()
    }

}