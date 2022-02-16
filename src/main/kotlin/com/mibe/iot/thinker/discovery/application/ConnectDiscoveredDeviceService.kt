package com.mibe.iot.thinker.discovery.application

import com.mibe.iot.thinker.discovery.application.exception.DeviceNotFoundException
import com.mibe.iot.thinker.discovery.application.port.from.ConnectDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.application.port.from.GetDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.application.port.from.GetSavedDevicePort
import com.mibe.iot.thinker.discovery.application.port.from.SaveDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.application.port.to.ConnectDiscoveredDeviceUseCase
import com.mibe.iot.thinker.discovery.domain.DeviceConnectionData
import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

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
            ?: throw DeviceNotFoundException("Device with address=$address wasn't found")

        val device = if (getSavedDevicePort.existsByAddress(address)) {
            getSavedDevicePort.getDeviceByAddress(address)
        } else {
            saveDiscoveredDevicePort.saveDiscoveredDevice(discoveredDevice)
        }

        addDeviceToConnections(device)
    }

    override suspend fun setConnectableDevices(devices: Flow<Device>) {
        val context = coroutineContext
        val connectableDevices = devices.toList().associateWith {
            Pair(
                getOnConnectionSuccessCallback(it, context),
                getOnConnectionFailedCallback(it, context)
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
        CoroutineScope(context).launch {
            saveDiscoveredDevicePort.updateDeviceStatus(
                device.id!!,
                DeviceStatus.CONFIGURED
            )
        }
    }

    private fun getOnConnectionFailedCallback(device: Device, context: CoroutineContext): () -> Unit = {
        CoroutineScope(context).launch {
            saveDiscoveredDevicePort.updateDeviceStatus(
                device.id!!,
                DeviceStatus.CONFIGURATION_FAILED
            )
        }
    }

}