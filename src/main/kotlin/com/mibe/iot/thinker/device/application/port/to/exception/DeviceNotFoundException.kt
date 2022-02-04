package com.mibe.iot.thinker.device.application.port.to.exception

/**
 * The Device not found exception. Indicates the situation, when required device was not received
 */
class DeviceNotFoundException(val deviceId: String, message: String = "") : Exception(message)
