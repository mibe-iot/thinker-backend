package com.mibe.iot.thinker.app.device.to.web.exception

import com.mibe.iot.thinker.domain.exception.DataNotFoundException

class DeviceReportIllegalPageException(val page: Int, val deviceId: String, message: String = "") :
    DataNotFoundException(message)