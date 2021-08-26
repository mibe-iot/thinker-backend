package com.mibe.iot.thinker.device.application.port.to.exception

/**
 * The Device not found exception
 */
class DeviceNotFoundException(val id: String, message: String = "") : Exception(message)
