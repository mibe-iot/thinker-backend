package com.mibe.iot.thinker.service.device.port

import com.mibe.iot.thinker.domain.device.Device

/**
 * The out port for updating device state
 */
interface UpdateDevicePort {
    suspend fun updateDevice(device: Device): Device
}
