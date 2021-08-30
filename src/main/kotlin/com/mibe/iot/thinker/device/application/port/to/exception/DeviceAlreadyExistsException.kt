package com.mibe.iot.thinker.device.application.port.to.exception

class DeviceAlreadyExistsException(val name: String, message: String = "") : Exception(message)
