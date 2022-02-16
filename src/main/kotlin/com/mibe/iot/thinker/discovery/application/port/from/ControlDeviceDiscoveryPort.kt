package com.mibe.iot.thinker.discovery.application.port.from

import com.mibe.iot.thinker.discovery.domain.DeviceConnectionData

interface ControlDeviceDiscoveryPort {
    suspend fun startDiscovery(connectionData: DeviceConnectionData)
    fun stopDiscovery()
    fun isDiscovering(): Boolean
}