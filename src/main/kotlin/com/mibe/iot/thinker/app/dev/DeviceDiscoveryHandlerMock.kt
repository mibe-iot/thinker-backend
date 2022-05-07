package com.mibe.iot.thinker.app.dev

import com.mibe.iot.thinker.PROFILE_DEV
import com.mibe.iot.thinker.domain.device.Device
import com.mibe.iot.thinker.domain.discovery.DeviceConfigurationCallbacks
import com.mibe.iot.thinker.domain.discovery.DeviceConnectionData
import com.mibe.iot.thinker.domain.discovery.DiscoveredDevice
import com.mibe.iot.thinker.service.discovery.port.ConnectDiscoveredDevicePort
import com.mibe.iot.thinker.service.discovery.port.ControlDeviceDiscoveryPort
import com.mibe.iot.thinker.service.discovery.port.GetDiscoveredDevicePort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import mu.KotlinLogging
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.concurrent.ThreadLocalRandom

@Profile(PROFILE_DEV)
@Component
class DeviceDiscoveryHandlerMock() : GetDiscoveredDevicePort, ControlDeviceDiscoveryPort, ConnectDiscoveredDevicePort {
    private val log = KotlinLogging.logger {}
    override fun setConnectableDevices(devicesAndActions: Map<Device, DeviceConfigurationCallbacks>) {
        log.warn { "dev mode: setConnectableDevices()" }
    }

    override fun addConnectableDevices(device: Device, deviceConfigurationCallbacks: DeviceConfigurationCallbacks) {
        log.warn { "dev mode: addConnectableDevices()" }
    }

    override fun removeConnectableDevice(device: Device) {
        log.warn { "dev mode: removeConnectableDevice()" }
    }

    override suspend fun startDiscovery() {
        log.warn { "dev mode: startDiscovery()" }
    }

    override suspend fun updateConnectionData(connectionData: DeviceConnectionData) {
        log.warn { "dev mode: updateConnectionData()" }
    }

    override fun stopDiscovery() {
        log.warn { "dev mode: stopDiscovery()" }
    }

    override fun isDiscovering(): Boolean {
        log.warn { "dev mode: isDiscovering() = always true" }
        return true
    }

    override fun getDiscoveryStartedTime(): LocalDateTime {
        log.warn { "dev mode: getDiscoveryStartedTime() = always now()" }
        return LocalDateTime.now()
    }

    override suspend fun getDiscoveredDevices(): Flow<DiscoveredDevice> {
        log.warn { "dev mode: getDiscoveredDevices() = always empty flow" }
        return emptyFlow()
    }

    override suspend fun getConnectedDeviceByAddress(address: String): DiscoveredDevice {
        log.warn { "dev mode: getConnectedDeviceByAddress() = sample device" }
        return DiscoveredDevice("aa:bb:cc:dd:ee:ff", LocalDateTime.now(), "Dd1:2134", -12, true)
    }

    override suspend fun isDeviceWaitingConfiguration(address: String): Boolean {
        log.warn { "dev mode: isDeviceWaitingConfiguration() = random boolean" }
        return ThreadLocalRandom.current().nextBoolean()
    }
}