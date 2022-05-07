package com.mibe.iot.thinker.app.discovery

import com.mibe.iot.thinker.domain.device.DeviceStatus
import com.mibe.iot.thinker.domain.discovery.DeviceConnectionData
import com.mibe.iot.thinker.service.discovery.ConnectDiscoveredDeviceUseCase
import com.mibe.iot.thinker.service.discovery.ControlDeviceDiscoveryUseCase
import com.mibe.iot.thinker.service.discovery.port.ControlDeviceDiscoveryPort
import com.mibe.iot.thinker.service.discovery.port.GetSavedDevicePort
import com.mibe.iot.thinker.service.settings.AppSettingsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.Executors

@Service
class ControlDeviceDiscoveryService
@Autowired constructor(
    private val connectDiscoveredDeviceUseCase: ConnectDiscoveredDeviceUseCase,
    private val getSavedDevicePort: GetSavedDevicePort,
    private val controlDeviceDiscoveryPort: ControlDeviceDiscoveryPort,
    private val appSettingsUseCase: AppSettingsUseCase
) : ControlDeviceDiscoveryUseCase {
    private val log = KotlinLogging.logger {}

    private val discoveryScope = CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())

    override fun isDiscovering(): Boolean {
        return controlDeviceDiscoveryPort.isDiscovering()
    }

    override suspend fun startDiscovery() {
        if (!controlDeviceDiscoveryPort.isDiscovering()) {
            log.debug("Starting discovery")
            refreshDeviceConnectionData()
            controlDeviceDiscoveryPort.startDiscovery()
            log.info { "Discovery started" }
        } else {
            log.info("Discovery is already launched")
        }
    }

    override suspend fun refreshDeviceConnectionData() {
        val connectionData = getConnectionData()
        log.info { "Got connection data from persistence. Connection data == null : ${connectionData == null}" }
        val connectableDevices = getSavedDevicePort.getByStatus(DeviceStatus.WAITING_CONFIGURATION)
        connectDiscoveredDeviceUseCase.setConnectableDevices(connectableDevices)
        controlDeviceDiscoveryPort.updateConnectionData(connectionData)
    }

    override fun stopDiscovery() {
        if (controlDeviceDiscoveryPort.isDiscovering()) {
            log.debug { "Stopping discovery" }
            controlDeviceDiscoveryPort.stopDiscovery()
            log.info { "Discovery stopped" }
        } else {
            log.warn { "Discovery isn't active" }
        }

    }

    override suspend fun restartDiscovery() {
        stopDiscovery()
        startDiscovery()
    }

    private suspend fun getConnectionData() = appSettingsUseCase.getAppSettings()
        ?.let { DeviceConnectionData(ssid = it.ssid.copyOf(), password = it.password.copyOf()) }
}
