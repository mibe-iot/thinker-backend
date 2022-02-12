package com.mibe.iot.thinker.discovery.adapter.from.persistence

import com.mibe.iot.thinker.discovery.application.port.from.SaveDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.domain.DiscoveredDevice
import com.mibe.iot.thinker.persistence.domain.DeviceEntity
import com.mibe.iot.thinker.persistence.repository.SpringDataDeviceRepository
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ConnectedDevicePersistenceAdapter
@Autowired constructor(
    private val repository: SpringDataDeviceRepository
) : SaveDiscoveredDevicePort {

    override suspend fun saveDiscoveredDevice(discoveredDevice: DiscoveredDevice, name: String): String {
        val entity = DeviceEntity(name = name, address = discoveredDevice.address)
        return repository.save(entity).awaitFirst()?.id ?: throw SaveDiscoveredDeviceException("Can't persist device")
    }

}