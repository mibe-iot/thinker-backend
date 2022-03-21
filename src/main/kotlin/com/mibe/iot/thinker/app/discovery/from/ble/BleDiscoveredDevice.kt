package com.mibe.iot.thinker.app.discovery.from.ble

import com.mibe.iot.thinker.domain.discovery.DiscoveredDevice
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