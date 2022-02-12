package com.mibe.iot.thinker.discovery.adapter.from.ble

import com.mibe.iot.thinker.discovery.domain.DiscoveredDevice
import com.welie.blessed.BluetoothGattService
import com.welie.blessed.BluetoothPeripheral
import java.time.LocalDateTime

data class BleDiscoveredDevice(
    val discoveredDevice: DiscoveredDevice,
    val services: List<BluetoothGattService>
)

fun BluetoothPeripheral.toDiscoveredDevice(discoveredAt: LocalDateTime) = DiscoveredDevice(
    name = this.name,
    address = this.address,
    discoveredAt = discoveredAt
)