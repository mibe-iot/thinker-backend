package com.mibe.iot.thinker.app.configuration

import com.mibe.iot.thinker.domain.configuration.WifiConfiguration
import com.mibe.iot.thinker.domain.exception.WifiConfigurationNotFoundException
import com.mibe.iot.thinker.service.configuration.WifiConfigurationUseCase
import com.mibe.iot.thinker.service.configuration.port.WifiConfigurationPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WifiConfigurationService
@Autowired constructor(
    private val wifiConfigurationPort: WifiConfigurationPort
) : WifiConfigurationUseCase{

    override suspend fun get(): WifiConfiguration {
        return wifiConfigurationPort.get() ?: throw WifiConfigurationNotFoundException()
    }

    override suspend fun update(wifiConfiguration: WifiConfiguration) {
        wifiConfigurationPort.update(wifiConfiguration)
    }

}