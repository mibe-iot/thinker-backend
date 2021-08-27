package com.mibe.iot.thinker.device.adapter.from.persistance

import com.mibe.iot.thinker.device.adapter.from.persistance.entity.toDevice
import com.mibe.iot.thinker.device.adapter.from.persistance.entity.toDeviceEntity
import com.mibe.iot.thinker.device.application.port.from.DeleteDevicePort
import com.mibe.iot.thinker.device.application.port.from.GetDevicePort
import com.mibe.iot.thinker.device.application.port.from.UpdateDevicePort
import com.mibe.iot.thinker.device.domain.Device
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class DevicePersistenceAdapter
@Autowired constructor(
    private val deviceRepository: SpringDataDeviceRepository
) : UpdateDevicePort, GetDevicePort, DeleteDevicePort {

    override fun updateDevice(device: Mono<Device>): Mono<Device> {
        return device.flatMap { deviceRepository.save(it.toDeviceEntity()) }
            .flatMap { it.toDevice().toMono() }
    }

    override fun deleteDevice(id: String): Mono<Void> {
        return deviceRepository.deleteById(id)
    }

    override fun getDevice(id: String): Mono<Device> {
        return deviceRepository.findById(id).flatMap {
            Mono.just(it.toDevice())
        }
    }

    override fun existsWithId(id: String): Mono<Boolean> {
        return deviceRepository.existsById(id)
    }

    override fun existsWithName(name: String): Mono<Boolean> {
        return deviceRepository.existsByName(name)
    }
}
