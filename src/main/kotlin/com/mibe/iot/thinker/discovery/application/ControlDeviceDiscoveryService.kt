package com.mibe.iot.thinker.discovery.application

import com.mibe.iot.thinker.discovery.application.port.from.ControlDeviceDiscoveryPort
import com.mibe.iot.thinker.discovery.application.port.to.ControlDeviceDiscoveryUseCase
import com.mibe.iot.thinker.discovery.domain.DeviceConnectionData
import java.util.concurrent.Executors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ControlDeviceDiscoveryService
@Autowired constructor(
    private val controlDeviceDiscoveryPort: ControlDeviceDiscoveryPort
) : ControlDeviceDiscoveryUseCase {
    private val log = KotlinLogging.logger {}

    private val discoveryScope = CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())

    override fun startDiscovery() {
        if (!controlDeviceDiscoveryPort.isDiscovering()) {
            log.debug("Starting discovery")

            val connectionData = DeviceConnectionData(
                ssid = "***".toByteArray(),
                password = "***encrypted***".toByteArray()
            )
            discoveryScope.launch { controlDeviceDiscoveryPort.startDiscovery(connectionData) }
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
}