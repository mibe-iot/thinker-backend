package com.mibe.iot.thinker.discovery.application.port.from

interface ControlDeviceDiscoveryPort {
    suspend fun startDiscovery()
    fun stopDiscovery(gracefully: Boolean = false)
    fun isDiscovering(): Boolean
}