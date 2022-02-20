package com.mibe.iot.thinker.device.adapter.from.persistance

import com.mibe.iot.thinker.device.application.port.from.DeleteDevicePort
import com.mibe.iot.thinker.device.application.port.from.GetDevicePort
import com.mibe.iot.thinker.device.application.port.from.UpdateDevicePort
import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.persistence.domain.DeviceEntity
import com.mibe.iot.thinker.persistence.repository.SpringDataDeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

/**
 * Adapter to provide device with operations mapped to persistence layer
 */
@Component
class DevicePersistenceAdapter
@Autowired constructor(
    private val deviceRepository: SpringDataDeviceRepository
) : UpdateDevicePort, GetDevicePort, DeleteDevicePort {

    override fun updateDevice(device: Mono<Device>): Mono<Device> {
        return device.flatMap { deviceRepository.save(it.toDeviceEntity()) }
            .flatMap { it.toDevice().toMono() }
    }

    /**
     * Gets all devices from repository.
     *
     * @return [Flow] of [Device]s
     */
    override fun getAllDevices(): Flow<Device> {
        return deviceRepository.findAll().asFlow().map(DeviceEntity::toDevice)
    }

    override fun deleteDevice(id: String): Mono<Void> {
        return deviceRepository.deleteById(id)
    }

    override suspend fun getDevice(id: String): Device? = deviceRepository.findById(id).awaitSingleOrNull()?.toDevice()

    override fun existsWithId(id: String): Mono<Boolean> {
        return deviceRepository.existsById(id)
    }

    override fun existsWithName(name: String): Mono<Boolean> {
        return deviceRepository.existsByName(name)
    }
}
