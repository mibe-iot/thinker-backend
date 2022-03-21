package com.mibe.iot.thinker.service.configuration

import com.mibe.iot.thinker.domain.configuration.WifiConfiguration

interface WifiConfigurationUseCase {

    suspend fun get(): WifiConfiguration
    suspend fun update(wifiConfiguration: WifiConfiguration)

}