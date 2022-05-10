package com.mibe.iot.thinker.service.hooks

import com.mibe.iot.thinker.domain.device.DeviceReport
import com.mibe.iot.thinker.domain.hooks.Trigger
import kotlinx.coroutines.flow.Flow

interface TriggerUseCase {

    suspend fun executeHookForReport(deviceReport: DeviceReport)
    suspend fun getAllTriggers(): Flow<Trigger>
    suspend fun getAllDeviceTriggers(deviceId: String): Flow<Trigger>
}