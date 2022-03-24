package com.mibe.iot.thinker.service.configuration.port

import com.mibe.iot.thinker.domain.configuration.WifiConfiguration

interface WifiConfigurationPort {

    suspend fun get(): WifiConfiguration?
    suspend fun update(wifiConfiguration: WifiConfiguration): WifiConfiguration

}