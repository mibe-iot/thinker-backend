package com.mibe.iot.thinker.cache.application.port.from

import com.mibe.iot.thinker.web.domain.IpAddress

interface ReadDeviceIpPort {
    fun readDeviceIp(deviceId: String): IpAddress?
}
