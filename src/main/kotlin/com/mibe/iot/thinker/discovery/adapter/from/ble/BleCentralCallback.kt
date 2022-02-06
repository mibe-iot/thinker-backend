package com.mibe.iot.thinker.discovery.adapter.from.ble

import com.welie.blessed.BluetoothCentralManagerCallback
import com.welie.blessed.BluetoothPeripheral
import com.welie.blessed.ScanResult
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

@Component
class BleCentralCallback : BluetoothCentralManagerCallback() {
    private val log = KotlinLogging.logger {}

    val discoveredPeripherals: MutableMap<String, Pair<BluetoothPeripheral, LocalDateTime>> = ConcurrentHashMap()

    override fun onDiscoveredPeripheral(peripheral: BluetoothPeripheral, scanResult: ScanResult) {
        log.debug {
            "discovered device: address=${peripheral.address} " +
                    "name=${peripheral.name} uuids=${peripheral.device?.uuids}"
        }
        discoveredPeripherals[peripheral.address] = Pair(peripheral, LocalDateTime.now())
    }

}