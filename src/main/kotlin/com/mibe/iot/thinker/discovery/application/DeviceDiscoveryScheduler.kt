package com.mibe.iot.thinker.discovery.application

import com.mibe.iot.thinker.discovery.application.port.from.ControlDeviceDiscoveryPort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import javax.annotation.PreDestroy
import kotlin.coroutines.coroutineContext

@Component
class DeviceDiscoveryScheduler
@Autowired constructor(
    private val controlDeviceDiscoveryUseCase: ControlDeviceDiscoveryPort
) {
    private val log = KotlinLogging.logger{}
    private var currentJob: Job? = null

    /**
     * This job will periodically start discovery for some time to update discovered devices.
     * Runs every minute for 10 seconds.
     * @see [org.springframework.scheduling.support.CronExpression.parse]
     */
    @Scheduled(cron = "0 * * * * *")
    fun runPeriodicalDiscovery() {
        if(controlDeviceDiscoveryUseCase.isDiscovering()) return
        log.info { "Periodical device discovery started" }
        runBlocking {
            currentJob = launch {
                controlDeviceDiscoveryUseCase.startDiscovery()
                delay(10_000)
                //Won't shut down discovery if it was run by other component
                controlDeviceDiscoveryUseCase.stopDiscovery(gracefully = true)
            }
        }
        log.debug { "Periodical device discovery ended" }
    }

    @PreDestroy
    fun cancelJobBeforeDestroy() {
        currentJob?.cancel()
    }

}

