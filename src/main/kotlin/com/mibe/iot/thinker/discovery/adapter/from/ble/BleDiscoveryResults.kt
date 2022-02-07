package com.mibe.iot.thinker.discovery.adapter.from.ble

import com.welie.blessed.BluetoothPeripheral
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.ConcurrentHashMap

@Component
class BleDiscoveryResults(
    val noticedDevices: MutableMap<String, Pair<BluetoothPeripheral, LocalDateTime>> = ConcurrentHashMap(),
    val allowedAddresses: MutableSet<String> = HashSet(),
    val devicesWithServices: MutableMap<String, BleDiscoveredDevice> = ConcurrentHashMap()
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