package com.mibe.iot.thinker.service.discovery.port

import com.mibe.iot.thinker.discovery.domain.DeviceConnectionData
import java.time.LocalDateTime

interface ControlDeviceDiscoveryPort {
    suspend fun startDiscovery(connectionData: DeviceConnectionData)
    fun stopDiscovery()
    fun isDiscovering(): Boolean
    fun getDiscoveryStartedTime(): LocalDateTime
}