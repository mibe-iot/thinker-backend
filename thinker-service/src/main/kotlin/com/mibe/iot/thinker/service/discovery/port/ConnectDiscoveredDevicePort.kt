package com.mibe.iot.thinker.service.discovery.port

import com.mibe.iot.thinker.domain.device.Device

interface ConnectDiscoveredDevicePort {
    fun setConnectableDevices(devicesAndActions: Map<Device, Pair<() -> Unit, () -> Unit>>)
    fun addConnectableDevices(device: Device, onConnectionSuccess: () -> Unit, onConnectionFailure: () -> Unit)
    fun removeConnectableDevice(device: Device)
}