package com.mibe.iot.thinker.discovery.application.port.from

interface ConnectDiscoveredDevicePort {
    suspend fun connectDevice(address: String)
}