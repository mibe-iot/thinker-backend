package com.mibe.iot.thinker.discovery.application.port.from

import com.mibe.iot.thinker.domain.device.Device

interface GetSavedDevicePort {
    suspend fun getDeviceByAddress(address: String): Device
    suspend fun existsByAddress(address: String): Boolean
}