package com.mibe.iot.thinker.persistence.entity

import com.mibe.iot.thinker.domain.device.DeviceConnectType
import com.mibe.iot.thinker.domain.device.DeviceStatus
import com.mibe.iot.thinker.domain.device.UNCLASSIFIED
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("devices")
data class DeviceEntity(
    @Id
    var id: String? = null,
    @Indexed(unique = true)
    var name: String? = null,
    var address: String,
    var description: String = "",
    var status: DeviceStatus = DeviceStatus.WAITING_CONFIGURATION,
    var connectType: DeviceConnectType = DeviceConnectType.MANUAL,
    var configurationHash: Int? = null,
    var actions: Set<DeviceActionEntity> = emptySet(),
    var deviceClass: String = UNCLASSIFIED,
    var reportTypes: Set<String> = emptySet()
)
