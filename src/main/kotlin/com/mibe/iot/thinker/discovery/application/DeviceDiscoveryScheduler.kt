package com.mibe.iot.thinker.discovery.application

import com.mibe.iot.thinker.discovery.application.port.from.ControlDeviceDiscoveryPort
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class DeviceDiscoveryScheduler
@Autowired constructor(
    private val controlDeviceDiscoveryUseCase: ControlDeviceDiscoveryPort
) {

    /**
     * This job will periodically start discovery for some time to update discovered devices.
     * Runs with 5 minutes interval.
     * @see org.springframework.scheduling.support.CronExpression#parse(String)
     */
    @Scheduled(cron = "* */5 * * * *")
    fun runPeriodicalDiscovery() {
        runBlocking {
            controlDeviceDiscoveryUseCase.startDiscovery()
            delay(10_000)
            //Won't shut down discovery if it was run by other component
            controlDeviceDiscoveryUseCase.stopDiscovery(gracefully = true)
        }
    }

}

