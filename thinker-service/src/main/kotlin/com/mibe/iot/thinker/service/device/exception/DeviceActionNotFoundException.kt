package com.mibe.iot.thinker.service.device.exception

import com.mibe.iot.thinker.domain.exception.DataNotFoundException

class DeviceActionNotFoundException(val deviceId: String? = null, val actionName: String, message: String = "") :
    DataNotFoundException(message)
