package com.mibe.iot.thinker.discovery.application

import com.mibe.iot.thinker.discovery.application.port.from.ControlDeviceDiscoveryPort
import com.mibe.iot.thinker.discovery.application.port.from.GetDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.application.port.to.ControlDeviceDiscoveryUseCase
import com.mibe.iot.thinker.discovery.application.port.to.GetDiscoveredDeviceUseCase
import com.mibe.iot.thinker.discovery.domain.DiscoveredDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.Executors

@Service
class GetDiscoveredDeviceService
@Autowired constructor(
    private val getDiscoveredDevicePort: GetDiscoveredDevicePort,
    private val controlDeviceDiscoveryPort: ControlDeviceDiscoveryPort
) : GetDiscoveredDeviceUseCase, ControlDeviceDiscoveryUseCase {
    private val log = KotlinLogging.logger {}

    private val discoveryScope = CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())

    override fun startDiscovery() {
        if (!controlDeviceDiscoveryPort.isDiscovering()) {
            log.debug("Starting discovery")

            discoveryScope.launch {
                controlDeviceDiscoveryPort.startDiscovery()
            }
            log.info { "Discovery started" }
        } else {
            log.info("Discovery is already launched")
        }
    }

    override fun stopDiscovery() {
        log.debug { "Stopping discovery" }

        if (controlDeviceDiscoveryPort.isDiscovering()) controlDeviceDiscoveryPort.stopDiscovery()

        log.info { "Discovery stopped" }
    }

    override suspend fun getDiscoveredDevices(): Flow<DiscoveredDevice> {
        return getDiscoveredDevicePort.getDiscoveredDevices()
    }
}