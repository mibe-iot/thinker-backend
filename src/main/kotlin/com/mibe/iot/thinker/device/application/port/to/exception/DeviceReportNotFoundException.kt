package com.mibe.iot.thinker.device.application.port.to.exception

import com.mibe.iot.thinker.domain.exception.DataNotFoundException

/**
 * The Device Report not found exception. Indicates the situation, when required device report was not found
 */
class DeviceReportNotFoundException(val reportId: String, val deviceId: String, message: String = "") :
    DataNotFoundException(message)