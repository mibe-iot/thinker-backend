package com.mibe.iot.thinker.device.application

import com.mibe.iot.thinker.device.application.port.from.GetDevicePort
import com.mibe.iot.thinker.device.application.port.from.UpdateDevicePort
import com.mibe.iot.thinker.device.application.port.to.UpdateDeviceUseCase
import com.mibe.iot.thinker.device.application.port.to.exception.DeviceNotFoundException
import com.mibe.iot.thinker.device.domain.DeviceUpdates
import com.mibe.iot.thinker.device.domain.receiveUpdates
import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.validation.validateDevice
import com.mibe.iot.thinker.validation.application.throwOnInvalid
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

/**
 * Update device service
 *
 * @property updateDevicePort any implementation of [UpdateDevicePort]
 * @constructor Create Update device service
 */
@Service
class UpdateDeviceService @Autowired constructor(
    private val updateDevicePort: UpdateDevicePort,
    private val getDevicePort: GetDevicePort
) : UpdateDeviceUseCase {

    override suspend fun updateDevice(deviceUpdates: DeviceUpdates): Device {
        val device = getDevicePort.getDevice(deviceUpdates.id)
            ?: throw DeviceNotFoundException(deviceUpdates.id)
        val updatedDevice = device.receiveUpdates(deviceUpdates)
        validateDevice(updatedDevice).throwOnInvalid()
        return updateDevicePort.updateDevice(Mono.just(updatedDevice)).awaitSingle()
    }
}
