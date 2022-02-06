package com.mibe.iot.thinker.discovery.application

import com.mibe.iot.thinker.discovery.application.port.from.ControlDeviceDiscoveryPort
import com.mibe.iot.thinker.discovery.application.port.from.GetDiscoveredDevicePort
import com.mibe.iot.thinker.discovery.application.port.to.ControlDeviceDiscoveryUseCase
import com.mibe.iot.thinker.discovery.application.port.to.GetDiscoveredDeviceUseCase
import com.mibe.iot.thinker.discovery.domain.DiscoveredDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.Executors

@Service
class GetDiscoveredDeviceService
@Autowired constructor(
    private val getDiscoveredDevicePort: GetDiscoveredDevicePort,
    private val controlDeviceDiscoveryPort: ControlDeviceDiscoveryPort
) : GetDiscoveredDeviceUseCase, ControlDeviceDiscoveryUseCase {

    private val discoveryScope = CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())

    override fun startDiscovery() {
        discoveryScope.launch {
            controlDeviceDiscoveryPort.startDiscovery()
        }
    }

    override fun stopDiscovery() {
        runBlocking {
            withTimeoutOrNull(30_000) {
                controlDeviceDiscoveryPort.stopDiscovery()
            }
        }
    }

    override suspend fun getDiscoveredDevices(): Flow<DiscoveredDevice> {
        TODO("Not yet implemented")
    }
}