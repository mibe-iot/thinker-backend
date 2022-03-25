package com.mibe.iot.thinker.app.device

import com.mibe.iot.thinker.service.device.port.GetDevicePort
import com.mibe.iot.thinker.service.device.GetDeviceUseCase
import com.mibe.iot.thinker.service.device.exception.DeviceNotFoundException
import com.mibe.iot.thinker.app.discovery.domain.validation.validateAddress
import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceAction
import com.mibe.iot.thinker.validation.application.throwOnInvalid
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
class GetDeviceService
@Autowired constructor(
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

    override suspend fun getDeviceActions(id: String): Flow<DeviceAction> {
        return getDevicePort.getDeviceActions(id)
            ?: throw DeviceNotFoundException(id)
    }

    /**
     * Finds device by MAC address
     *
     * @param address device MAC address
     * @return [Device] found by address
     */
    override suspend fun getDeviceByAddress(address: String): Device {
        validateAddress(address).throwOnInvalid()
        return getDevicePort.getDeviceByAddress(address)
            ?: throw DeviceNotFoundException()
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
