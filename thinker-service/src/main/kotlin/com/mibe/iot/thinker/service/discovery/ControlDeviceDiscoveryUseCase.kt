package com.mibe.iot.thinker.service.discovery

/**
 * Control device discovery use case. Operations to control discovery process.
 */
interface ControlDeviceDiscoveryUseCase {

    fun isDiscovering(): Boolean

    /**
     * Starts discovery process.
     */
    suspend fun startDiscovery()

    suspend fun refreshDeviceConnectionData()

    /**
     * Stops discovery process
     */
    fun stopDiscovery()

    suspend fun restartDiscovery()
}