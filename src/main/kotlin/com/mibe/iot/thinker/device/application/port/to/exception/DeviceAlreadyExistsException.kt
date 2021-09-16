package com.mibe.iot.thinker.device.application.port.to.exception

class DeviceAlreadyExistsException(val deviceName: String, message: String = "") : Exception(message)
