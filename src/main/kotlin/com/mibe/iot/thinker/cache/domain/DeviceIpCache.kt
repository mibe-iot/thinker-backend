package com.mibe.iot.thinker.cache.domain

import com.mibe.iot.thinker.web.domain.IpAddress
import io.github.reactivecircus.cache4k.Cache
import org.springframework.stereotype.Component

@Component
class DeviceIpCache {
    val cache = Cache.Builder().build<String, IpAddress>()
}
