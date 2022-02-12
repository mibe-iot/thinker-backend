package com.mibe.iot.thinker.discovery.application.port.to

interface ConnectDiscoveredDeviceUseCase {
    suspend fun connectDeviceByAddress(address: String)
}