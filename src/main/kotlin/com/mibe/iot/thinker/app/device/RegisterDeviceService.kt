package com.mibe.iot.thinker.app.device

import com.mibe.iot.thinker.service.device.port.UpdateDevicePort
import com.mibe.iot.thinker.service.device.RegisterDeviceUseCase
import com.mibe.iot.thinker.service.device.exception.DeviceAlreadyExistsException
import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.validation.validateNewDevice
import com.mibe.iot.thinker.service.device.port.GetDevicePort
import com.mibe.iot.thinker.validation.application.mapToErrorMonoIfInvalid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

/**
 * Register device service
 *
 * @property updateDevicePort any implementation of [UpdateDevicePort]
 * @constructor Create Register device service
 */
@Service
class RegisterDeviceService @Autowired constructor(
    private val updateDevicePort: UpdateDevicePort,
    private val getDevicePort: GetDevicePort
) : RegisterDeviceUseCase {

    /**
     * Validates device and passes it to the [UpdateDevicePort].
     *
     * @param deviceMono [Mono] of the [Device] to register
     * @return [Device] with updated info such given id
     */
    override fun registerDevice(deviceMono: Mono<Device>): Mono<Device> {
        return deviceMono.flatMap {
            mapToErrorMonoIfInvalid(it, validateNewDevice)
        }.flatMap { device ->
            getDevicePort.existsWithName(device.name).flatMap { exists ->
                if (exists)
                    Mono.error(DeviceAlreadyExistsException(device.name))
                else
                    Mono.just(device)
            }
        }.flatMap { device -> updateDevicePort.updateDevice(Mono.just(device)) }
    }
}
