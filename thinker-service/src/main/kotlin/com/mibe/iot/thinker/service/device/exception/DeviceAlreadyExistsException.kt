package com.mibe.iot.thinker.service.device.exception

class DeviceAlreadyExistsException(val deviceName: String, message: String = "") : Exception(message)
