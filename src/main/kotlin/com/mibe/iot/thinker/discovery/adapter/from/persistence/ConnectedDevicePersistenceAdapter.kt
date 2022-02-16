package com.mibe.iot.thinker.discovery.adapter.from.persistence

import com.mibe.iot.thinker.device.adapter.from.persistance.toDevice
import com.mibe.iot.thinker.discovery.application.port.from.SaveDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.domain.DiscoveredDevice
import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.persistence.domain.DeviceEntity
import com.mibe.iot.thinker.persistence.repository.SpringDataDeviceRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
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

}