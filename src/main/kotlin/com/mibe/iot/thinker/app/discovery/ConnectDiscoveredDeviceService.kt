package com.mibe.iot.thinker.app.discovery

import com.mibe.iot.thinker.app.discovery.exception.DiscoveredDeviceNotFoundException
import com.mibe.iot.thinker.service.discovery.port.ConnectDiscoveredDevicePort
import com.mibe.iot.thinker.service.discovery.port.GetDiscoveredDevicePort
import com.mibe.iot.thinker.service.discovery.port.GetSavedDevicePort
import com.mibe.iot.thinker.service.discovery.port.SaveDiscoveredDevicePort
import com.mibe.iot.thinker.service.discovery.ConnectDiscoveredDeviceUseCase
import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceStatus
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
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
        val context = coroutineContext
        val connectableDevices = devices.toList().associateWith { device ->
            Pair(
                getOnConnectionSuccessCallback(device, context),
                getOnConnectionFailedCallback(device, context)
            )
        }
        connectDiscoveredDevicePort.setConnectableDevices(connectableDevices)
    }

    private suspend fun addDeviceToConnections(device: Device) {
        val context = coroutineContext
        connectDiscoveredDevicePort.addConnectableDevices(
            device,
            onConnectionSuccess = getOnConnectionSuccessCallback(device, context),
            onConnectionFailure = getOnConnectionFailedCallback(device, context)
        )
    }

    private fun getOnConnectionSuccessCallback(device: Device, context: CoroutineContext): () -> Unit = {
        log.info { "Device successfully connected: $device" }
        CoroutineScope(context).launch {
            saveDiscoveredDevicePort.updateDeviceStatus(
                device.id!!,
                DeviceStatus.CONFIGURED
            )
            connectDiscoveredDevicePort.removeConnectableDevice(device)
        }
    }

    private fun getOnConnectionFailedCallback(device: Device, context: CoroutineContext): () -> Unit = {
        log.info { "Device connection failed: $device" }
        CoroutineScope(context).launch {
            saveDiscoveredDevicePort.updateDeviceStatus(
                device.id!!,
                DeviceStatus.CONFIGURATION_FAILED
            )
            connectDiscoveredDevicePort.removeConnectableDevice(device)
        }
    }

}