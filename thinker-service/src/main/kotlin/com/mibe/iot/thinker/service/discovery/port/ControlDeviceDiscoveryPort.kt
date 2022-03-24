package com.mibe.iot.thinker.service.discovery.port

import com.mibe.iot.thinker.domain.discovery.DeviceConnectionData
import java.time.LocalDateTime

interface ControlDeviceDiscoveryPort {
    suspend fun startDiscovery()
    suspend fun updateConnectionData(connectionData: DeviceConnectionData)
    fun stopDiscovery()
    fun isDiscovering(): Boolean
    fun getDiscoveryStartedTime(): LocalDateTime
}