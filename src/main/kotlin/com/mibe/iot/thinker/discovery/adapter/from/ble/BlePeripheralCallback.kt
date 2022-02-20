package com.mibe.iot.thinker.discovery.adapter.from.ble

import com.welie.blessed.BluetoothCommandStatus
import com.welie.blessed.BluetoothGattCharacteristic
import com.welie.blessed.BluetoothGattService
import com.welie.blessed.BluetoothPeripheral
import com.welie.blessed.BluetoothPeripheralCallback
import java.util.*
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BlePeripheralCallback
@Autowired constructor(
    private val discoveryDataHolder: DiscoveryDataHolder
) : BluetoothPeripheralCallback() {
    private val log = KotlinLogging.logger {}

    override fun onServicesDiscovered(
        peripheral: BluetoothPeripheral,
        services: MutableList<BluetoothGattService>
    ) {
        log.info { "Discovered services for ${peripheral.address}: ${services.map { it.uuid }}" }

        val connectionData = discoveryDataHolder.connectionData!!

        fun BluetoothPeripheral.writeCharacteristic(uuid: String, value: ByteArray) = this.writeCharacteristic(
            UUID.fromString(BIT_SERVICE),
            UUID.fromString(uuid),
            value,
            BluetoothGattCharacteristic.WriteType.WITHOUT_RESPONSE
        )

        val name = discoveryDataHolder.discoveredDevices[peripheral.address]?.name!!
        peripheral.writeCharacteristic(BIT_CHARACTERISTIC_NAME, name.toByteArray())
        peripheral.writeCharacteristic(BIT_CHARACTERISTIC_SSID, connectionData.ssid)
        peripheral.writeCharacteristic(BIT_CHARACTERISTIC_PASSWORD, connectionData.password)
    }

    override fun onCharacteristicWrite(
        peripheral: BluetoothPeripheral,
        value: ByteArray,
        characteristic: BluetoothGattCharacteristic,
        status: BluetoothCommandStatus
    ) {
        val address = peripheral.address
        if (status == BluetoothCommandStatus.COMMAND_SUCCESS) {
            val characteristicsState = discoveryDataHolder.deviceCharacteristicsConfigured
                .computeIfAbsent(address) { DiscoveryDataHolder.CharacteristicsState() }
            characteristicsState.setCharacteristicWritten(characteristic.uuid)
            log.info { "Characteristic uuid=${characteristic.uuid} successfully written" }
            characteristicsState.run {
                if (isNameWritten && isSsidWritten && isPasswordWritten) {
                    discoveryDataHolder.deviceConfigurationCallbacks[address]?.let { it.onConfigurationCompleted() }
                    peripheral.cancelConnection()
                }
            }
        } else {
            discoveryDataHolder.deviceConfigurationCallbacks[address]?.let { it.onConfigurationFailed() }
            peripheral.cancelConnection()
        }
    }

}