package com.mibe.iot.thinker.device.application

import com.mibe.iot.thinker.device.application.port.from.GetDevicePort
import com.mibe.iot.thinker.device.application.port.to.GetDeviceUseCase
import com.mibe.iot.thinker.device.application.port.to.exception.DeviceNotFoundException
import com.mibe.iot.thinker.domain.device.Device
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

/**
 * Get device service
 *
 * @property getDevicePort any implementation of [GetDevicePort]
 * @constructor Create Get device service
 */
@Service
class GetDeviceService @Autowired constructor(
    private val getDevicePort: GetDevicePort
) : GetDeviceUseCase {

    /**
     * Get device by its id with help of [GetDeviceUseCase.getDevice]
     *
     * @param id the id of the [Device]
     * @return [Mono] of the founded [Device]
     * @throws [DeviceNotFoundException] if no device was found by the [id]
     */
    override fun getDevice(id: String): Mono<Device> {
        return getDevicePort.getDevice(id).switchIfEmpty(Mono.error(DeviceNotFoundException(id)))
    }

    /**
     * Get all devices by calling [GetDevicePort.getAllDevices]
     *
     * @return [Flow] of all persisted [Device]s
     */
    override fun getAllDevices(): Flow<Device> {
        return getDevicePort.getAllDevices()
    }
}
