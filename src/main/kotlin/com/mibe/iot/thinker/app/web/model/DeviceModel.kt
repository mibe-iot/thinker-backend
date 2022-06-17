package com.mibe.iot.thinker.app.web.model

import com.mibe.iot.thinker.domain.device.DeviceAction
import com.mibe.iot.thinker.domain.device.DeviceConnectType
import com.mibe.iot.thinker.domain.device.DeviceReport
import com.mibe.iot.thinker.domain.device.DeviceStatus
import com.mibe.iot.thinker.domain.device.DeviceWithReport

data class DeviceModel(
    var id: String,
    val name: String,
    val address: String,
    val description: String,
    val deviceClass: String?,
    var status: DeviceStatus,
    var connectType: DeviceConnectType,
    var actions: Set<DeviceAction>,
    val reportTypes: Set<String>,
    var latestReport: DeviceReport?
) {
    companion object {
        fun from(deviceWithReport: DeviceWithReport): DeviceModel {
            return deviceWithReport.device.run {
                DeviceModel(
                    id = id!!,
                    name = name,
                    address = address,
                    description = description,
                    deviceClass = deviceClass,
                    status = status,
                    connectType = connectType,
                    actions = actions.toSet(),
                    reportTypes = HashSet(reportTypes),
                    latestReport = deviceWithReport.deviceReport
                )
            }
        }
    }
}