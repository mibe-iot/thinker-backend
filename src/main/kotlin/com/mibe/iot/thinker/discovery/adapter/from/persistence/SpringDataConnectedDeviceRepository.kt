package com.mibe.iot.thinker.discovery.adapter.from.persistence

import com.mibe.iot.thinker.discovery.adapter.from.persistence.entity.ConnectedDeviceEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface SpringDataConnectedDeviceRepository : ReactiveMongoRepository<ConnectedDeviceEntity, String>