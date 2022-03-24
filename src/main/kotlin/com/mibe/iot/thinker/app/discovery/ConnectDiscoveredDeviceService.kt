package com.mibe.iot.thinker.app.discovery

import com.mibe.iot.thinker.app.discovery.exception.DiscoveredDeviceNotFoundException
import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceStatus
import com.mibe.iot.thinker.domain.discovery.DeviceConfigurationCallbacks
import com.mibe.iot.thinker.service.discovery.ConnectDiscoveredDeviceUseCase
import com.mibe.iot.thinker.service.discovery.port.ConnectDiscoveredDevicePort
import com.mibe.iot.thinker.service.discovery.port.GetDiscoveredDevicePort
import com.mibe.iot.thinker.service.discovery.port.GetSavedDevicePort
import com.mibe.iot.thinker.service.discovery.port.SaveDiscoveredDevicePort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConnectDiscoveredDeviceService
@Autowired constructor(
    private val saveDiscoveredDevicePort: SaveDiscoveredDevicePort,
    private val getSavedDevicePort: GetSavedDevicePort,
    private val getDiscoveredDevicePort: GetDiscoveredDevicePort,
    private val connectDiscoveredDevicePort: ConnectDiscoveredDevicePort
) : ConnectDiscoveredDeviceUseCase {
    private val log = KotlinLogging.logger {}

    override suspend fun connectDeviceByAddress(address: String) {
        val discoveredDevice = getDiscoveredDevicePort.getConnectedDeviceByAddress(address)
            ?: throw DiscoveredDeviceNotFoundException(address)

        val device = if (getSavedDevicePort.existsByAddress(address)) {
            getSavedDevicePort.getDeviceByAddress(address)
        } else {
            saveDiscoveredDevicePort.saveDiscoveredDevice(discoveredDevice)
        }

        addDeviceToConnections(device)
    }

    override suspend fun setConnectableDevices(devices: Flow<Device>) {
        val connectableDevices = devices.toList().associateWith { device ->
            DeviceConfigurationCallbacks(
                onConfigurationSucceeded = getOnConfigurationSuccessCallback(device),
                onConfigurationFailed = getOnConfigurationFailedCallback(device)
            )
        }
        connectDiscoveredDevicePort.setConnectableDevices(connectableDevices)
    }

    private suspend fun addDeviceToConnections(device: Device) {
        connectDiscoveredDevicePort.addConnectableDevices(
            device,
            DeviceConfigurationCallbacks(
                onConfigurationSucceeded = getOnConfigurationSuccessCallback(device),
                onConfigurationFailed = getOnConfigurationFailedCallback(device)
            )
        )
    }

    private fun getOnConfigurationSuccessCallback(device: Device): (Int) -> Unit = { configurationHash ->
        log.info { "Device successfully connected: $device" }
        runBlocking {
            saveDiscoveredDevicePort.updateDeviceStatus(
                device.id!!,
                DeviceStatus.CONFIGURED,
                configurationHash
            )
            connectDiscoveredDevicePort.removeConnectableDevice(device)
        }
    }

    private fun getOnConfigurationFailedCallback(device: Device): () -> Unit = {
        log.info { "Device connection failed: $device" }
        runBlocking {
            saveDiscoveredDevicePort.updateDeviceStatus(
                device.id!!,
                DeviceStatus.CONFIGURATION_FAILED
            )
            connectDiscoveredDevicePort.removeConnectableDevice(device)
        }
    }

}