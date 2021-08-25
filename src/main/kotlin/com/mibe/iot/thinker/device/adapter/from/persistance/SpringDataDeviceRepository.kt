package com.mibe.iot.thinker.device.adapter.from.persistance

import com.mibe.iot.thinker.device.adapter.from.persistance.entity.DeviceEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface SpringDataDeviceRepository : ReactiveMongoRepository<DeviceEntity, String>
