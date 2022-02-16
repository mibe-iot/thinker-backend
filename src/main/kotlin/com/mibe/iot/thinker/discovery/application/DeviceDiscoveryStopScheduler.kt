package com.mibe.iot.thinker.discovery.application

import com.mibe.iot.thinker.discovery.application.port.from.ControlDeviceDiscoveryPort
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.Executors
import javax.annotation.PreDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class DeviceDiscoveryStopScheduler
@Autowired constructor(
    private val controlDeviceDiscoveryUseCase: ControlDeviceDiscoveryPort
) {
    private val log = KotlinLogging.logger {}

    private val discoveryScope = CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())

    /**
     * This job will periodically try to stop discovery if it's running for longer than 5 minutes.
     * Runs every minute for 10 seconds.
     * @see [org.springframework.scheduling.support.CronExpression.parse]
     */
    @Scheduled(cron = "0 * * * * *")
    fun tryStopDiscovery() {
        if (controlDeviceDiscoveryUseCase.isDiscovering()) return
        log.debug { "Trying to stop discovery" }
        discoveryScope.launch {
            val startedAt = controlDeviceDiscoveryUseCase.getDiscoveryStartedTime()
            val isDiscovering = controlDeviceDiscoveryUseCase.isDiscovering()
            //Stop discovery if it is running for longer than 5 minutes
            if(isDiscovering && Duration.between(LocalDateTime.now(), startedAt).toMinutes() > 5)
            controlDeviceDiscoveryUseCase.stopDiscovery()
        }
    }

    @PreDestroy
    fun cancelJobBeforeDestroy() {
        discoveryScope.cancel()
    }

}

