package com.mibe.iot.thinker.cache.adapter.from.cache

import com.mibe.iot.thinker.cache.application.port.from.ReadDeviceIpPort
import com.mibe.iot.thinker.cache.domain.DeviceIpCache
import com.mibe.iot.thinker.web.domain.IpAddress
import org.springframework.beans.factory.annotation.Autowired

class DeviceIpCacheAdapter @Autowired constructor(
    private val deviceIpCache: DeviceIpCache
): ReadDeviceIpPort {
    override fun readDeviceIp(deviceId: String): IpAddress? {
        return deviceIpCache.cache.get(deviceId)
    }
}