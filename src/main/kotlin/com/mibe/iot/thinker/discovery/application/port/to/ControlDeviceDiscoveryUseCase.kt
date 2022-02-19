package com.mibe.iot.thinker.discovery.application.port.to

/**
 * Control device discovery use case. Operations to control discovery process.
 */
interface ControlDeviceDiscoveryUseCase {

    /**
     * Starts discovery process.
     */
    fun startDiscovery()

    /**
     * Stops discovery process
     */
    fun stopDiscovery()
}