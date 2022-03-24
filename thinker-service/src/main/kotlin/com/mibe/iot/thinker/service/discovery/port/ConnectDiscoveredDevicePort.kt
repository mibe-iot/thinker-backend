package com.mibe.iot.thinker.service.discovery.port

import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.discovery.DeviceConfigurationCallbacks

interface ConnectDiscoveredDevicePort {
    fun setConnectableDevices(devicesAndActions: Map<Device, DeviceConfigurationCallbacks>)
    fun addConnectableDevices(device: Device, deviceConfigurationCallbacks: DeviceConfigurationCallbacks)
    fun removeConnectableDevice(device: Device)
}