package com.mibe.iot.thinker.discovery.adapter.from.ble

import com.welie.blessed.BluetoothCommandStatus
import com.welie.blessed.BluetoothGattCharacteristic
import com.welie.blessed.BluetoothGattService
import com.welie.blessed.BluetoothPeripheral
import com.welie.blessed.BluetoothPeripheralCallback
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class BlePeripheralCallback
@Autowired constructor(
    private val bleDiscoveryDataHolder: BleDiscoveryDataHolder
) : BluetoothPeripheralCallback() {
    private val log = KotlinLogging.logger {}

    override fun onServicesDiscovered(
        peripheral: BluetoothPeripheral,
        services: MutableList<BluetoothGattService>
    ) {
        val discoveredAt = LocalDateTime.now()
        log.info { "Discovered services for ${peripheral.address}: ${services.map{it.uuid} }" }
        val bleDiscoveredDevice = BleDiscoveredDevice(
            peripheral.toDiscoveredDevice(discoveredAt),
            services = services.toList()
        )
        bleDiscoveryDataHolder.devicesWithServices[peripheral.address] = bleDiscoveredDevice
        val connectionData = bleDiscoveryDataHolder.deviceConnectionData[peripheral.address]!!
        log.info { bleDiscoveredDevice }
        peripheral.writeCharacteristic(
            UUID.fromString(BIT_SERVICE),
            UUID.fromString(BIT_CHARACTERISTIC_NAME),
            connectionData.deviceName.toByteArray(),
            BluetoothGattCharacteristic.WriteType.WITHOUT_RESPONSE
        )
        peripheral.writeCharacteristic(
            UUID.fromString(BIT_SERVICE),
            UUID.fromString(BIT_CHARACTERISTIC_SSID),
            connectionData.ssid.toByteArray(),
            BluetoothGattCharacteristic.WriteType.WITHOUT_RESPONSE
        )
        peripheral.writeCharacteristic(
            UUID.fromString(BIT_SERVICE),
            UUID.fromString(BIT_CHARACTERISTIC_PASSWORD),
            connectionData.password.toByteArray(),
            BluetoothGattCharacteristic.WriteType.WITHOUT_RESPONSE
        )
    }

    override fun onCharacteristicWrite(
        peripheral: BluetoothPeripheral,
        value: ByteArray,
        characteristic: BluetoothGattCharacteristic,
        status: BluetoothCommandStatus
    ) {
        if (characteristic.uuid == UUID.fromString(BIT_CHARACTERISTIC_PASSWORD)) {
            peripheral.cancelConnection()
            bleDiscoveryDataHolder.allowedAddresses -= peripheral.address
            bleDiscoveryDataHolder.devicesToReconnect -= peripheral.address
        }
    }
}