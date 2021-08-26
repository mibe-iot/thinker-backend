package com.mibe.iot.thinker.device.application

import com.mibe.iot.thinker.device.application.port.from.UpdateDevicePort
import com.mibe.iot.thinker.device.application.port.to.UpdateDeviceUseCase
import com.mibe.iot.thinker.device.domain.Device
import com.mibe.iot.thinker.device.domain.validation.validateDevice
import com.mibe.iot.thinker.validation.application.mapToErrorMonoIfInvalid
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
    private val updateDevicePort: UpdateDevicePort
) : UpdateDeviceUseCase {

    /**
     * Validate and update device with help of [UpdateDevicePort.updateDevice]
     *
     * @param device [Mono] of [Device] to update
     * @return [Mono] of updated [Device]
     */
    override fun updateDevice(device: Mono<Device>): Mono<Device> {
        return device.map {
            mapToErrorMonoIfInvalid(it, validateDevice)
        }.flatMap { updateDevicePort.updateDevice(it) }
    }
}
