package com.mibe.iot.thinker.app.discovery.from.ble

import com.welie.blessed.BluetoothCommandStatus
import com.welie.blessed.BluetoothGattCharacteristic
import com.welie.blessed.BluetoothGattService
import com.welie.blessed.BluetoothPeripheral
import com.welie.blessed.BluetoothPeripheralCallback
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

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

        val connectionData = discoveryDataHolder.connectionData

        if (connectionData == null) {
            log.warn { "Connection data is uninitialized. Please configure application settings" }
            return
        }
        if (!services.any { it.uuid == UUID.fromString(BIT_SERVICE) }) {
            log.error { "Can't connect to ${peripheral.address}: doesn't have main connection service" }
            invalidateDevice(peripheral)
        }


        fun BluetoothPeripheral.writeCharacteristic(uuid: String, value: ByteArray) = this.writeCharacteristic(
            UUID.fromString(BIT_SERVICE),
            UUID.fromString(uuid),
            value,
            BluetoothGattCharacteristic.WriteType.WITHOUT_RESPONSE
        )

        val id = discoveryDataHolder.connectableDevices.first { peripheral.address == it.address }.id!!
        peripheral.writeCharacteristic(BIT_CHARACTERISTIC_NAME, id.toByteArray())
        peripheral.writeCharacteristic(BIT_CHARACTERISTIC_SSID, String(connectionData.ssid).toByteArray())
        peripheral.writeCharacteristic(BIT_CHARACTERISTIC_PASSWORD, String(connectionData.password).toByteArray())
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
                    log.info { "All characteristics written successfully" }
                    discoveryDataHolder.deviceConfigurationCallbacks[address]?.let {
                        it.onConfigurationSucceeded(discoveryDataHolder.connectionData.hashCode())
                    }
                    peripheral.cancelConnection()
                }
            }
        } else {
            log.error { "Error while trying to write characteristics" }
            invalidateDevice(peripheral)
        }
    }

    override fun onBondingFailed(peripheral: BluetoothPeripheral) {
        log.error { "Error while trying to connect to peripheral. Removing this device from connectable list" }
        invalidateDevice(peripheral)
    }

    private fun invalidateDevice(peripheral: BluetoothPeripheral) {
        discoveryDataHolder.deviceConfigurationCallbacks[peripheral.address]?.let { it.onConfigurationFailed() }
        peripheral.cancelConnection()
    }
}