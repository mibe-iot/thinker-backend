package com.mibe.iot.thinker.discovery.adapter.from.persistence

import com.mibe.iot.thinker.device.adapter.from.persistance.toDevice
import com.mibe.iot.thinker.discovery.application.port.from.GetSavedDevicePort
import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceStatus
import com.mibe.iot.thinker.persistence.repository.SpringDataDeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class GetSavedDeviceAdapter
@Autowired constructor(
    private val deviceRepository: SpringDataDeviceRepository
) : GetSavedDevicePort {

    override suspend fun getDeviceByAddress(address: String): Device {
        return deviceRepository.findByAddress(address).awaitSingle().toDevice()
    }

    override suspend fun existsByAddress(address: String): Boolean {
        return deviceRepository.existsByAddress(address).awaitSingle()
    }

    override suspend fun getByStatus(status: DeviceStatus): Flow<Device> {
        return deviceRepository.findByDeviceStatus(status).asFlow().map { it.toDevice() }
    }
}