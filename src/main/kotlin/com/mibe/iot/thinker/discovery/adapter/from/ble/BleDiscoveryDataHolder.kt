package com.mibe.iot.thinker.discovery.adapter.from.ble

import com.mibe.iot.thinker.discovery.domain.DeviceConnectionData
import com.welie.blessed.BluetoothPeripheral
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentSkipListSet

@Component
class BleDiscoveryDataHolder(
    val noticedDevices: MutableMap<String, Pair<BluetoothPeripheral, LocalDateTime>> = ConcurrentHashMap(),
    val allowedAddresses: MutableSet<String> = ConcurrentSkipListSet(),
    val devicesWithServices: MutableMap<String, BleDiscoveredDevice> = ConcurrentHashMap(),
    val deviceConnectionData: MutableMap<String, DeviceConnectionData> = ConcurrentHashMap(),
    val devicesToReconnect: MutableSet<String> = ConcurrentSkipListSet()
) {
    fun isDiscovered(address: String) = devicesWithServices.containsKey(address)
    fun isAllowedToConnect(address: String) = allowedAddresses.contains(address)

    fun isDiscoveryDataOutdated(address: String, timeout: Int): Boolean {
        if (!isDiscovered(address)) return true
        return ChronoUnit.SECONDS.between(
            LocalDateTime.now(),
            devicesWithServices[address]!!.discoveredDevice.discoveredAt
        ) > timeout
    }
}