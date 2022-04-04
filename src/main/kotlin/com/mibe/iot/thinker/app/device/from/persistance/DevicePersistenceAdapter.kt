package com.mibe.iot.thinker.app.device.from.persistance

import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceAction
import com.mibe.iot.thinker.domain.device.DeviceStatus
import com.mibe.iot.thinker.domain.device.DeviceUpdates
import com.mibe.iot.thinker.persistence.entity.DeviceEntity
import com.mibe.iot.thinker.persistence.repository.SpringDataDeviceRepository
import com.mibe.iot.thinker.service.device.port.DeleteDevicePort
import com.mibe.iot.thinker.service.device.port.GetDevicePort
import com.mibe.iot.thinker.service.device.port.UpdateDevicePort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

/**
 * Adapter to provide device with operations mapped to persistence layer
 */
@Component
class DevicePersistenceAdapter
@Autowired constructor(
    private val deviceRepository: SpringDataDeviceRepository,
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) : UpdateDevicePort, GetDevicePort, DeleteDevicePort {
    private val log = KotlinLogging.logger {}

    override suspend fun getDevice(id: String): Device? = deviceRepository.findById(id).awaitSingleOrNull()?.toDevice()

    override suspend fun getDeviceActions(id: String): Flow<DeviceAction>? {
        return deviceRepository.findById(id).awaitSingleOrNull()?.actions?.map { it.toDeviceAction() }?.asFlow()
    }

    override suspend fun getDeviceByAddress(address: String): Device? =
        deviceRepository.findByAddress(address).awaitSingleOrNull()?.toDevice()


    override suspend fun updateDevice(device: Device): Device {
        return deviceRepository.save(device.toDeviceEntity()).awaitSingle().toDevice()
    }

    override suspend fun updateDevicePartially(deviceId: String, updateData: Map<*, *>) {
        val query = Query.query(Criteria.where("id").`is`(deviceId))
        val update = Update().apply {
            updateData.forEach { set(it.key as String, it.value) }
        }
        reactiveMongoTemplate.updateFirst(query, update, DeviceEntity::class.java).awaitSingleOrNull()
    }

    override suspend fun updateStatusByIds(deviceIds: Flow<String>, newStatus: DeviceStatus) {
        val query = Query.query(Criteria.where("id").`in`(deviceIds.toList()))
        val update = Update().apply { set("status", newStatus) }
        reactiveMongoTemplate.updateMulti(query, update, DeviceEntity::class.java)
    }

    override suspend fun updateAdditionalData(deviceAdditionalData: DeviceUpdates) {
        log.debug { "deviceAdditionalData: $deviceAdditionalData" }
        val query = Query.query(Criteria.where("id").`is`(deviceAdditionalData.id))
        val update = Update().apply {
            set(
                "actions",
                HashSet(deviceAdditionalData.actions?.map { it.toDeviceActionEntity() }?.toSet() ?: emptySet())
            )
            set("deviceClass", deviceAdditionalData.deviceClass)
            set("reportTypes", deviceAdditionalData.reportTypes)
        }
        reactiveMongoTemplate.updateFirst(query, update, DeviceEntity::class.java).awaitSingleOrNull()
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
        deviceRepository.deleteById(id).awaitSingleOrNull()
    }

    override suspend fun existsWithId(id: String): Boolean {
        return deviceRepository.existsById(id).awaitSingle()
    }

    override fun existsWithName(name: String): Mono<Boolean> {
        return deviceRepository.existsByName(name)
    }

    override suspend fun getAllWithDifferentHash(configurationHash: Int): Flow<Device> {
        return deviceRepository.findAllByConfigurationHashNot(configurationHash).asFlow().map { it.toDevice() }
    }

    override suspend fun getAllDevicesByStatusIn(allowedStatuses: Set<DeviceStatus>): Flow<Device> {
        return deviceRepository.findAllByStatusIn(allowedStatuses).asFlow().map(DeviceEntity::toDevice)
    }
}
