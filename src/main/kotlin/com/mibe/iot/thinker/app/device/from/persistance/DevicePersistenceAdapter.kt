package com.mibe.iot.thinker.app.device.from.persistance

import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.persistence.entity.DeviceEntity
import com.mibe.iot.thinker.persistence.repository.SpringDataDeviceRepository
import com.mibe.iot.thinker.service.device.port.DeleteDevicePort
import com.mibe.iot.thinker.service.device.port.GetDevicePort
import com.mibe.iot.thinker.service.device.port.UpdateDevicePort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

/**
 * Adapter to provide device with operations mapped to persistence layer
 */
@Component
class DevicePersistenceAdapter
@Autowired constructor(
    private val deviceRepository: SpringDataDeviceRepository
) : UpdateDevicePort, GetDevicePort, DeleteDevicePort {

    override suspend fun getDevice(id: String): Device? = deviceRepository.findById(id).awaitSingleOrNull()?.toDevice()

    override suspend fun getDeviceByAddress(address: String): Device? =
        deviceRepository.findByAddress(address).awaitSingleOrNull()?.toDevice()


    override suspend fun updateDevice(device: Device): Device {
        return deviceRepository.save(device.toDeviceEntity()).awaitSingle().toDevice()
    }

    /**
     * Gets all devices from repository.
     *
     * @return [Flow] of [Device]s
     */
    override fun getAllDevices(): Flow<Device> {
        return deviceRepository.findAll().asFlow().map(DeviceEntity::toDevice)
    }

    override suspend fun deleteDevice(id: String) {
        deviceRepository.deleteById(id).awaitSingle()
    }

    override suspend fun existsWithId(id: String): Boolean {
        return deviceRepository.existsById(id).awaitSingle()
    }

    override fun existsWithName(name: String): Mono<Boolean> {
        return deviceRepository.existsByName(name)
    }
}
