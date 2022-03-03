package com.mibe.iot.thinker.app.discovery.adapter.from.persistence

import com.mibe.iot.thinker.service.device.adapter.from.persistance.toDevice
import com.mibe.iot.thinker.service.discovery.port.SaveDiscoveredDevicePort
import com.mibe.iot.thinker.domain.discovery.DiscoveredDevice
import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceStatus
import com.mibe.iot.thinker.persistence.domain.DeviceEntity
import com.mibe.iot.thinker.persistence.repository.SpringDataDeviceRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ConnectedDevicePersistenceAdapter
@Autowired constructor(
    private val repository: SpringDataDeviceRepository
) : SaveDiscoveredDevicePort {

    override suspend fun saveDiscoveredDevice(discoveredDevice: DiscoveredDevice): Device {
        val entity = DeviceEntity(address = discoveredDevice.address)
        return repository.save(entity).awaitSingle().toDevice()
    }

    override suspend fun updateDeviceStatus(deviceId: String, deviceStatus: DeviceStatus) {
        val device = repository.findById(deviceId).awaitSingle()
        device.status = deviceStatus
        repository.save(device)
    }
}