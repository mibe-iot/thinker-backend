package com.mibe.iot.thinker.device.application

import com.mibe.iot.thinker.device.application.port.from.GetDevicePort
import com.mibe.iot.thinker.device.application.port.to.GetDeviceUseCase
import com.mibe.iot.thinker.device.application.port.to.exception.DeviceNotFoundException
import com.mibe.iot.thinker.domain.device.Device
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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
     * Get device by its id.
     *
     * @param id the id of the [Device]
     * @return found device or null
     */
    override suspend fun getDevice(id: String): Device {
        return getDevicePort.getDevice(id)
            ?: throw DeviceNotFoundException(id)
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
