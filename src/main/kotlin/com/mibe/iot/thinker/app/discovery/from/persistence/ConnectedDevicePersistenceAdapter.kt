package com.mibe.iot.thinker.app.discovery.from.persistence

import com.mibe.iot.thinker.app.device.from.persistance.toDevice
import com.mibe.iot.thinker.service.discovery.port.SaveDiscoveredDevicePort
import com.mibe.iot.thinker.domain.discovery.DiscoveredDevice
import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceStatus
import com.mibe.iot.thinker.persistence.entity.DeviceEntity
import com.mibe.iot.thinker.persistence.repository.SpringDataDeviceRepository
import kotlinx.coroutines.reactor.awaitSingle
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.updateFirst
import org.springframework.stereotype.Component

@Component
class ConnectedDevicePersistenceAdapter
@Autowired constructor(
    private val repository: SpringDataDeviceRepository,
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) : SaveDiscoveredDevicePort {
    private val log = KotlinLogging.logger{}

    override suspend fun saveDiscoveredDevice(discoveredDevice: DiscoveredDevice): Device {
        val entity = DeviceEntity(address = discoveredDevice.address, name = discoveredDevice.name)
        return repository.save(entity).awaitSingle().toDevice()
    }

    override suspend fun updateDeviceStatus(deviceId: String, deviceStatus: DeviceStatus, configurationHash: Int?) {
        log.info { "Updating device status by id=$deviceId" }

        val query = Query.query(Criteria.where("id").`is`(deviceId))
        val update = Update().apply { set("status", deviceStatus); set("configurationHash", configurationHash) }
        reactiveMongoTemplate.updateFirst(query, update, DeviceEntity::class.java).awaitSingle()
    }

}