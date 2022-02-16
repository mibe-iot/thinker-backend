package com.mibe.iot.thinker.domain.device

/**
 * Device represents a single device in IoT network (bit)
 */
data class Device(
    var id: String?,
    val name: String,
    val address: String,
    val description: String = "",
    var status: DeviceStatus = DeviceStatus.WAITING_CONFIGURATION,
    var connectType: DeviceConnectType = DeviceConnectType.MANUAL,
    var actions: Set<DeviceAction> = emptySet()
)
