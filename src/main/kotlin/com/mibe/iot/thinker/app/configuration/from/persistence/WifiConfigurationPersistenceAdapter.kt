package com.mibe.iot.thinker.app.configuration.from.persistence

import com.mibe.iot.thinker.domain.configuration.ConfigurationType
import com.mibe.iot.thinker.domain.configuration.WifiConfiguration
import com.mibe.iot.thinker.persistence.repository.SpringDataConfigurationRepository
import com.mibe.iot.thinker.service.configuration.port.WifiConfigurationPort
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Autowired

class WifiConfigurationPersistenceAdapter
@Autowired constructor(
    private val configurationRepository: SpringDataConfigurationRepository
) : WifiConfigurationPort{

    override suspend fun get(): WifiConfiguration {
        return configurationRepository.getByType(ConfigurationType.WIFI).awaitSingleOrNull()?.toWifiConfig()
            ?: WifiConfiguration("", "")
    }

    override suspend fun update(wifiConfiguration: WifiConfiguration): WifiConfiguration {
        return configurationRepository.updateByType(wifiConfiguration.toConfigEntity(), ConfigurationType.WIFI)
            .awaitSingle().toWifiConfig()
    }
}