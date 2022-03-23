package com.mibe.iot.thinker.domain.discovery

import java.time.LocalDateTime

data class DiscoveredDevice(
    val address: String,
    val discoveredAt: LocalDateTime,
    val name: String
)
