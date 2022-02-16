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
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
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

    private suspend fun addDeviceToConnections(device: Device) {
        val context = coroutineContext
        connectDiscoveredDevicePort.addConnectableDevices(device,
            onConnectionSuccess = {
                CoroutineScope(context).launch {
                    saveDiscoveredDevicePort.updateDeviceStatus(
                        device.id!!,
                        DeviceStatus.CONFIGURED
                    )
                }
            },
            onConnectionFailure = {
                CoroutineScope(context).launch {
                    saveDiscoveredDevicePort.updateDeviceStatus(
                        device.id!!,
                        DeviceStatus.CONFIGURATION_FAILED
                    )
                }
            })
    }

    private suspend fun reconnectDeviceByAddress(connectionData: DeviceConnectionData) {
        connectDiscoveredDevicePort.reconnectDevice(connectionData)
    }
}