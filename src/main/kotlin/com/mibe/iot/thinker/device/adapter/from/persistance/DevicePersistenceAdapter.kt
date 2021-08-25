package com.mibe.iot.thinker.device.adapter.from.persistance

import com.mibe.iot.thinker.device.adapter.from.persistance.entity.toDevice
import com.mibe.iot.thinker.device.adapter.from.persistance.entity.toDeviceEntity
import com.mibe.iot.thinker.device.application.port.from.UpdateDevicePort
import com.mibe.iot.thinker.device.domain.Device
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class DevicePersistenceAdapter
@Autowired constructor(
    private val deviceRepository: SpringDataDeviceRepository
) : UpdateDevicePort {

    override fun updateDevice(device: Mono<Device>): Mono<Device> {
        val updatedDeviceEntity = device.flatMap { deviceRepository.save(it.toDeviceEntity()) }
        return updatedDeviceEntity.flatMap { Mono.just(it.toDevice()) }
    }
}
