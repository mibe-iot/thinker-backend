package com.mibe.iot.thinker.service.hooks.port

import com.mibe.iot.thinker.domain.hooks.Trigger
import kotlinx.coroutines.flow.Flow

interface TriggerPort {

    fun getTriggersByDeviceIdAndHookIds(deviceId: String, hookIds: List<String>): Flow<Trigger>

    suspend fun getByDeviceIdAndReportType(deviceId: String, reportType: String): Trigger?

    suspend fun getAllTriggers(): Flow<Trigger>

    suspend fun getAllDeviceTriggers(deviceId: String): Flow<Trigger>

    fun createTriggers(triggers: Flow<Trigger>): Flow<Trigger>

    suspend fun deleteTriggerById(id: String)

    suspend fun deleteAllTriggersByHookId(hookId: String)

    suspend fun deleteAllTriggersByDeviceId(deviceId: String)

}