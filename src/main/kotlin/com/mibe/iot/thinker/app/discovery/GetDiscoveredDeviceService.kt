package com.mibe.iot.thinker.app.discovery

import com.mibe.iot.thinker.service.discovery.port.GetDiscoveredDevicePort
import com.mibe.iot.thinker.service.discovery.GetDiscoveredDeviceUseCase
import com.mibe.iot.thinker.domain.discovery.DiscoveredDevice
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GetDiscoveredDeviceService
@Autowired constructor(
    private val getDiscoveredDevicePort: GetDiscoveredDevicePort
) : GetDiscoveredDeviceUseCase {

    override suspend fun getDiscoveredDevices(): Flow<DiscoveredDevice> {
        return getDiscoveredDevicePort.getDiscoveredDevices()
    }
}