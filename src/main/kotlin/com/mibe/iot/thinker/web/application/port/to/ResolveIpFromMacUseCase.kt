package com.mibe.iot.thinker.web.application.port.to

import com.mibe.iot.thinker.web.domain.IpAddress

interface ResolveIpFromMacUseCase {
    fun resolveIpFromMac(mac: String): IpAddress
}