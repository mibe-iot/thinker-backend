package com.mibe.iot.thinker.discovery.domain

import java.time.LocalDateTime

data class DiscoveredDevice(
    val name: String,
    val address: String,
    val discoveredAt: LocalDateTime
)
