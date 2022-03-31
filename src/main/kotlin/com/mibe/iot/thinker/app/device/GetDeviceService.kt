package com.mibe.iot.thinker.app.device

import com.mibe.iot.thinker.app.discovery.validateAddress
import com.mibe.iot.thinker.service.device.port.GetDevicePort
import com.mibe.iot.thinker.service.device.GetDeviceUseCase
import com.mibe.iot.thinker.service.device.exception.DeviceNotFoundException
import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceAction
import com.mibe.iot.thinker.domain.device.DeviceStatus
import com.mibe.iot.thinker.domain.device.DeviceWithReport
import com.mibe.iot.thinker.service.device.port.GetDeviceReportPort
import com.mibe.iot.thinker.validation.application.throwOnInvalid
import kotlinx.coroutines.flow.*
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
    private val getDevicePort: GetDevicePort,
    private val getDeviceReportPort: GetDeviceReportPort
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
    override suspend fun getAllActiveDevices(): Flow<Device> {
        return getDevicePort.getAllDevicesByStatusIn(setOf(DeviceStatus.CONFIGURED, DeviceStatus.WAITING_CONFIGURATION))
    }

    override suspend fun getAllDevicesWithLatestReports(): Flow<DeviceWithReport> {
        return getAllActiveDevices().map { device ->
            DeviceWithReport(device, getDeviceReportPort.getLatestDeviceReport(device.id!!))
        }
    }
}
