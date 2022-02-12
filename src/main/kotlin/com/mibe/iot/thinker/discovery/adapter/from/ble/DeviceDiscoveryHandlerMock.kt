package com.mibe.iot.thinker.discovery.adapter.from.ble

import com.mibe.iot.thinker.constants.PROFILE_DEFAULT
import com.mibe.iot.thinker.constants.PROFILE_DEV
import com.mibe.iot.thinker.discovery.application.port.from.ConnectDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.application.port.from.ControlDeviceDiscoveryPort
import com.mibe.iot.thinker.discovery.application.port.from.GetDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.application.randomString
import com.mibe.iot.thinker.discovery.domain.DeviceConnectionData
import com.mibe.iot.thinker.discovery.domain.DiscoveredDevice
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

@Component
@Profile(PROFILE_DEV, PROFILE_DEFAULT)
class DeviceDiscoveryHandlerMock
@Autowired constructor(
    private val bleDiscoveryResultsHolder: BleDiscoveryResultsHolder
) : GetDiscoveredDevicePort, ControlDeviceDiscoveryPort, ConnectDiscoveredDevicePort {
    private val log = KotlinLogging.logger {}

    private val isActive = AtomicBoolean(false)

    override suspend fun startDiscovery() {
        isActive.set(true)
    }

    override fun stopDiscovery(gracefully: Boolean) {
        isActive.set(false)
    }

    override fun isDiscovering(): Boolean {
        return isActive.get()
    }

    override suspend fun getDiscoveredDevices(): Flow<DiscoveredDevice> {
        return (0..(Random().nextInt(4))).map {
            DiscoveredDevice(
                randomString(5),
                randomMacAddress(),
                LocalDateTime.now()
            )
        }.asFlow()
    }

    override suspend fun getConnectedDeviceByAddress(address: String): DiscoveredDevice? {
        return DiscoveredDevice(
            randomString(5),
            address,
            LocalDateTime.now().minusMinutes(1)
        )
    }

    override suspend fun connectDevice(discoveredDevice: DiscoveredDevice, connectionData: DeviceConnectionData) {
        log.info { "Connecting device with address=${discoveredDevice.address} via BLE" }
        delay(300)
        log.info { "Sending Name..." }
        delay(100)
        log.info { "Sending SSID..." }
        delay(200)
        log.info { "Sending Password..." }
        delay(200)
        log.info { "Ble device connected" }
    }

    private fun randomMacAddress(): String {
        return randomString(12).uppercase().chunked(2).joinToString(":")
    }

}