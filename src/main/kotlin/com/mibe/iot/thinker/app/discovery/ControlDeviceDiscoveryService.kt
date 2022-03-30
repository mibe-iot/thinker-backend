package com.mibe.iot.thinker.app.discovery

import com.mibe.iot.thinker.domain.device.DeviceStatus
import com.mibe.iot.thinker.domain.discovery.DeviceConnectionData
import com.mibe.iot.thinker.service.configuration.WifiConfigurationUseCase
import com.mibe.iot.thinker.service.device.UpdateDeviceUseCase
import com.mibe.iot.thinker.service.discovery.ConnectDiscoveredDeviceUseCase
import com.mibe.iot.thinker.service.discovery.ControlDeviceDiscoveryUseCase
import com.mibe.iot.thinker.service.discovery.port.ControlDeviceDiscoveryPort
import com.mibe.iot.thinker.service.discovery.port.GetSavedDevicePort
import java.util.concurrent.Executors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ControlDeviceDiscoveryService
@Autowired constructor(
    private val connectDiscoveredDeviceUseCase: ConnectDiscoveredDeviceUseCase,
    private val updateDeviceUseCase: UpdateDeviceUseCase,
    private val wifiConfigurationUseCase: WifiConfigurationUseCase,
    private val getSavedDevicePort: GetSavedDevicePort,
    private val controlDeviceDiscoveryPort: ControlDeviceDiscoveryPort
) : ControlDeviceDiscoveryUseCase {
    private val log = KotlinLogging.logger {}

    private val discoveryScope = CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())

    override fun isDiscovering(): Boolean {
        return controlDeviceDiscoveryPort.isDiscovering()
    }

    override fun startDiscovery() {
        if (!controlDeviceDiscoveryPort.isDiscovering()) {
            log.debug("Starting discovery")
            discoveryScope.launch {
                refreshDeviceConnectionData()
                controlDeviceDiscoveryPort.startDiscovery()
            }
            log.info { "Discovery started" }
        } else {
            log.info("Discovery is already launched")
        }
    }

    override suspend fun refreshDeviceConnectionData() {
        val connectionData = getConnectionData()
        log.info { "New connectionData: $connectionData" }
        val connectableDevices = getSavedDevicePort.getByStatus(DeviceStatus.WAITING_CONFIGURATION)
        connectDiscoveredDeviceUseCase.setConnectableDevices(connectableDevices)
        controlDeviceDiscoveryPort.updateConnectionData(connectionData)
    }

    override fun stopDiscovery() {
        if (controlDeviceDiscoveryPort.isDiscovering())  {
            log.debug { "Stopping discovery" }
            controlDeviceDiscoveryPort.stopDiscovery()
            log.info { "Discovery stopped" }
        } else {
            log.warn { "Discovery isn't active" }
        }

    }

    override fun restartDiscovery() {
        stopDiscovery()
        startDiscovery()
    }

    private suspend fun getConnectionData() = wifiConfigurationUseCase.get()
        .let { DeviceConnectionData(ssid = it.ssid.toByteArray(), password = it.password.toByteArray()) }
}
