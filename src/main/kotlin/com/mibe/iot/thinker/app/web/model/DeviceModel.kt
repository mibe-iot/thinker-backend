package com.mibe.iot.thinker.app.web.model

import com.mibe.iot.thinker.domain.device.*

data class DeviceModel (
    var id: String,
    val name: String,
    val address: String,
    val description: String,
    val deviceClass: String?,
    var status: DeviceStatus,
    var connectType: DeviceConnectType,
    var actions: Set<DeviceAction>,
    var latestReport: DeviceReport?
) {
    companion object{
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
                    latestReport = deviceWithReport.deviceReport
                )
            }
        }
    }
}