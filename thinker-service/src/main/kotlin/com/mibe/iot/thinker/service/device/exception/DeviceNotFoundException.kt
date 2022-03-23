package com.mibe.iot.thinker.service.device.exception

import com.mibe.iot.thinker.domain.exception.DataNotFoundException

/**
 * The Device not found exception. Indicates the situation, when required device was not found
 */
class DeviceNotFoundException(val deviceId: String? = null, message: String = "") : DataNotFoundException(message)
