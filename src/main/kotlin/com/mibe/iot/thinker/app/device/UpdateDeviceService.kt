package com.mibe.iot.thinker.app.device

import com.mibe.iot.thinker.domain.device.*
import com.mibe.iot.thinker.service.device.port.UpdateDevicePort
import com.mibe.iot.thinker.service.device.UpdateDeviceUseCase
import com.mibe.iot.thinker.service.device.exception.DeviceNotFoundException
import com.mibe.iot.thinker.service.device.port.GetDevicePort
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Update device service
 *
 * @property updateDevicePort any implementation of [UpdateDevicePort]
 * @constructor Create Update device service
 */
@Service
class UpdateDeviceService
@Autowired constructor(
    private val updateDevicePort: UpdateDevicePort,
    private val getDevicePort: GetDevicePort
) : UpdateDeviceUseCase {
    private val log = KotlinLogging.logger{}

    override suspend fun updateDevice(deviceUpdates: DeviceUpdates): Device {
        val device = getDevicePort.getDevice(deviceUpdates.id)
            ?: throw DeviceNotFoundException(deviceUpdates.id)
        val updatedDevice = device.receiveUpdates(deviceUpdates)
        return updateDevicePort.updateDevice(updatedDevice)
    }

    override suspend fun updateDeviceAdditionalData(deviceAdditionalData: DeviceUpdates) {
        val device = getDevicePort.getDevice(deviceAdditionalData.id)
            ?: throw DeviceNotFoundException(deviceAdditionalData.id)
        updateDevicePort.updateAdditionalData(deviceAdditionalData)
    }

}
