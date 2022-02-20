package com.mibe.iot.thinker.discovery.application.port.from

import com.mibe.iot.thinker.domain.device.Device

interface ConnectDiscoveredDevicePort {
    fun setConnectableDevices(devicesAndActions: Map<Device, Pair<() -> Unit, () -> Unit>>)
    fun addConnectableDevices(device: Device, onConnectionSuccess: () -> Unit, onConnectionFailure: () -> Unit)
    fun removeConnectableDevice(device: Device)
}