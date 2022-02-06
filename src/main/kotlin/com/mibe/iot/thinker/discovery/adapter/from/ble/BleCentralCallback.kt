package com.mibe.iot.thinker.discovery.adapter.from.ble

import com.welie.blessed.BluetoothCentralManagerCallback
import com.welie.blessed.BluetoothPeripheral
import com.welie.blessed.ScanResult
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class BleCentralCallback : BluetoothCentralManagerCallback() {
    private val log = KotlinLogging.logger {}

    override fun onDiscoveredPeripheral(peripheral: BluetoothPeripheral, scanResult: ScanResult) {
        log.info { "discovered: name=${peripheral.name} address=${peripheral.address} uuids=${peripheral.device?.uuids} device=${peripheral.device?.rawDevice}" }
    }
}