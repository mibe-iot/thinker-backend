package com.mibe.iot.thinker.app.configuration.from.persistence

import com.mibe.iot.thinker.domain.configuration.ConfigurationType
import com.mibe.iot.thinker.domain.configuration.WifiConfiguration
import com.mibe.iot.thinker.persistence.entity.ConfigurationEntity
import com.mibe.iot.thinker.service.configuration.PASSWORD
import com.mibe.iot.thinker.service.configuration.SSID

fun ConfigurationEntity.toWifiConfig() = WifiConfiguration(
    this.parameters[SSID] ?: "",
    this.parameters[PASSWORD] ?: ""
)

fun WifiConfiguration.toConfigEntity(id: String) = ConfigurationEntity(
    id = id,
    type = ConfigurationType.WIFI,
    parameters = mapOf(
        SSID to this.ssid,
        PASSWORD to this.password
    )
)