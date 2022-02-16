package com.mibe.iot.thinker.discovery.application.port.from

import com.mibe.iot.thinker.discovery.domain.DeviceConnectionData
import com.mibe.iot.thinker.domain.device.Device

interface ConnectDiscoveredDevicePort {
    suspend fun connectDevice(connectionData: DeviceConnectionData)
    suspend fun reconnectDevice(connectionData: DeviceConnectionData)
    fun setConnectableDevices(devicesAndActions: Map<Device, Pair<() -> Unit, () -> Unit>>)
    fun addConnectableDevices(device: Device, onConnectionSuccess: () -> Unit, onConnectionFailure: () -> Unit)
}