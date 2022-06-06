package com.mibe.iot.thinker.app.discovery

import com.mibe.iot.thinker.service.discovery.port.GetDiscoveredDevicePort
import com.mibe.iot.thinker.service.discovery.GetDiscoveredDeviceUseCase
import com.mibe.iot.thinker.domain.discovery.DiscoveredDevice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

@Service
class GetDiscoveredDeviceService
@Autowired constructor(
    private val getDiscoveredDevicePort: GetDiscoveredDevicePort
) : GetDiscoveredDeviceUseCase {

    override suspend fun getDiscoveredDevices(): Flow<DiscoveredDevice> {
        val now = LocalDateTime.now();
        return getDiscoveredDevicePort.getDiscoveredDevices()
            .filter { Duration.between(it.discoveredAt, now) < Duration.ofMinutes(5) }
    }
}