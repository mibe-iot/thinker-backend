package com.mibe.iot.thinker.persistence.domain

import com.mibe.iot.thinker.domain.device.DeviceConnectType
import com.mibe.iot.thinker.domain.device.DeviceStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("devices")
class DeviceEntity(
    @Id
    var id: String? = null,
    var name: String,
    var address: String,
    var description: String = "",
    var status: DeviceStatus = DeviceStatus.WAITING_CONFIGURATION,
    var connectType: DeviceConnectType = DeviceConnectType.MANUAL,
    var actions: Set<DeviceActionEntity> = emptySet()
)
