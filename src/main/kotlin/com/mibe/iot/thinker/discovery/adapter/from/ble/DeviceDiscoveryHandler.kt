package com.mibe.iot.thinker.discovery.adapter.from.ble

import com.mibe.iot.thinker.discovery.application.port.from.ControlDeviceDiscoveryPort
import com.mibe.iot.thinker.discovery.application.port.from.GetDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.domain.DiscoveredDevice
import com.welie.blessed.BluetoothCentralManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import javax.annotation.PostConstruct


@Component
class DeviceDiscoveryHandler
@Autowired constructor(
    private val bleCentralCallback: BleCentralCallback
) : GetDiscoveredDevicePort, ControlDeviceDiscoveryPort {

    @Value("\${thinker.ble.rssiThreshold:-130}")
    private lateinit var rssiThreshold: String

    private lateinit var central: BluetoothCentralManager
    private val isActive = AtomicBoolean(false)
    private val discoveryRequestsAmount = AtomicInteger(0)

    @PostConstruct
    private fun initCentralManager() {
        central = BluetoothCentralManager(bleCentralCallback)
        central.setRssiThreshold(rssiThreshold.toInt())
    }

    override suspend fun startDiscovery() {
        isActive.set(true)
        discoveryRequestsAmount.incrementAndGet()
        central.scanForPeripherals()
    }

    override fun stopDiscovery(gracefully: Boolean) {
        val requestsAmount = discoveryRequestsAmount.decrementAndGet()
        if (!gracefully || requestsAmount <= 0) {
            discoveryRequestsAmount.set(0)
            central.stopScan()
            isActive.set(false)
        }
    }

    override fun isDiscovering(): Boolean {
        return isActive.get()
    }

    override suspend fun getDiscoveredDevices(): Flow<DiscoveredDevice> {
        return bleCentralCallback.discoveredPeripherals.values.asFlow()
            .map { peripheralAndTime ->
                val discoveredAt = peripheralAndTime.second
                peripheralAndTime.first.let { peripheral ->
                    DiscoveredDevice(
                        peripheral.name.ifBlank { "[unknown]" },
                        peripheral.address,
                        discoveredAt
                    )
                }
            }
    }
}