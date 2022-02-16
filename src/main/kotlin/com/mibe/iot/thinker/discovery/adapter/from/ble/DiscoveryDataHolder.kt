package com.mibe.iot.thinker.discovery.adapter.from.ble

import com.mibe.iot.thinker.discovery.domain.DeviceConnectionData
import com.mibe.iot.thinker.discovery.domain.DiscoveredDevice
import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.device.DeviceConnectType
import com.mibe.iot.thinker.domain.device.DeviceStatus
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentSkipListSet
import org.springframework.stereotype.Component

@Component
class DiscoveryDataHolder(
    val discoveredDevices: MutableMap<String, DiscoveredDevice> = ConcurrentHashMap(),
    val connectableDevices: MutableSet<Device> = ConcurrentSkipListSet(),
    var deviceConfigurationCallbacks: Map<String, DeviceConfigurationCallbacks> = ConcurrentHashMap(),
    var connectionData: DeviceConnectionData?,
    val deviceCharacteristicsConfigured: MutableMap<String, CharacteristicsState> = ConcurrentHashMap()
) {
    fun shouldBeConfigured(address: String): Boolean {
        return connectableDevices.firstOrNull { it.address == address }
            ?.let {
                when {
                    it.status == DeviceStatus.WAITING_CONFIGURATION -> true
                    it.connectType == DeviceConnectType.AUTO -> true
                    else -> false
                }
            } ?: false
    }

    fun removeConnectableDevice(device: Device) {
        connectableDevices -= device
        deviceCharacteristicsConfigured -= device.address
        deviceConfigurationCallbacks -= device.address
    }

    data class CharacteristicsState(
        var isNameWritten: Boolean = false,
        var isSsidWritten: Boolean = false,
        var isPasswordWritten: Boolean = false
    ) {
        fun setCharacteristicWritten(uuid: UUID) {
            when (uuid.toString()) {
                BIT_CHARACTERISTIC_NAME -> isNameWritten = true
                BIT_CHARACTERISTIC_SSID -> isSsidWritten = true
                BIT_CHARACTERISTIC_PASSWORD -> isPasswordWritten = true
            }
        }
    }
}