package com.mibe.iot.thinker.discovery.application

import com.mibe.iot.thinker.discovery.application.port.from.ConnectDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.application.port.to.ConnectDiscoveredDeviceUseCase
import com.mibe.iot.thinker.discovery.domain.validation.validateAddress
import com.mibe.iot.thinker.validation.application.throwOnInvalid
import com.mibe.iot.thinker.validation.domain.ValidationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConnectDiscoveredDeviceService
@Autowired constructor(
    private val connectDiscoveredDevicePort: ConnectDiscoveredDevicePort
) : ConnectDiscoveredDeviceUseCase {

    override suspend fun connectDeviceByAddress(address: String) {
        connectDiscoveredDevicePort.connectDevice(address)
        // TODO save to persistence
    }
}