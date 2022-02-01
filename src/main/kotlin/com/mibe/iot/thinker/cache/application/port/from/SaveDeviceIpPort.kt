package com.mibe.iot.thinker.cache.application.port.from

import com.mibe.iot.thinker.web.domain.IpAddress

interface SaveDeviceIpPort {
    fun saveDeviceIp(deviceId: String, deviceIp: IpAddress)
}
