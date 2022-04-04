package com.mibe.iot.thinker.app.device

import com.fasterxml.jackson.databind.ObjectMapper
import com.mibe.iot.thinker.domain.device.DeviceUpdates
import com.mibe.iot.thinker.app.validation.validateDeviceUpdates
import com.mibe.iot.thinker.service.device.UpdateDeviceUseCase
import com.mibe.iot.thinker.service.device.exception.DeviceNotFoundException
import com.mibe.iot.thinker.service.device.port.GetDevicePort
import com.mibe.iot.thinker.service.device.port.UpdateDevicePort
import com.mibe.iot.thinker.validation.application.throwOnInvalid
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
    private val getDevicePort: GetDevicePort,
    private val objectMapper: ObjectMapper
) : UpdateDeviceUseCase {
    private val log = KotlinLogging.logger {}

    override suspend fun updateDevicePartially(deviceId: String, deviceUpdates: DeviceUpdates) {
        if (!getDevicePort.existsWithId(deviceId)) {
            throw DeviceNotFoundException(deviceId)
        }
        validateDeviceUpdates(deviceUpdates).throwOnInvalid()
        val properties: Map<*, *> = objectMapper.convertValue(deviceUpdates, Map::class.java)
        log.debug { "Receive update properties: $properties" }
        updateDevicePort.updateDevicePartially(deviceId, properties)
    }

    override suspend fun updateDeviceAdditionalData(deviceAdditionalData: DeviceUpdates) {
        if (!getDevicePort.existsWithId(deviceAdditionalData.id!!)) {
            throw DeviceNotFoundException(deviceAdditionalData.id)
        }
        updateDevicePort.updateAdditionalData(deviceAdditionalData)
    }

}
