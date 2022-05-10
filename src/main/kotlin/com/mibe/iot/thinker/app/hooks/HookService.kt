package com.mibe.iot.thinker.app.hooks

import com.mibe.iot.thinker.domain.hooks.Hook
import com.mibe.iot.thinker.domain.hooks.Trigger
import com.mibe.iot.thinker.service.device.exception.DeviceNotFoundException
import com.mibe.iot.thinker.service.device.port.GetDevicePort
import com.mibe.iot.thinker.service.hooks.HookUseCase
import com.mibe.iot.thinker.service.hooks.exception.HookNotFoundException
import com.mibe.iot.thinker.service.hooks.port.HookPort
import com.mibe.iot.thinker.service.hooks.port.TriggerPort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class HookService(
    private val getDevicePort: GetDevicePort,
    private val hookPort: HookPort,
    private val triggerPort: TriggerPort
) : HookUseCase {

    override suspend fun getHooks(): Flow<Hook> {
        return hookPort.getAllHooks()
    }

    override suspend fun getHookById(id: String): Hook {
        return hookPort.getHookById(id) ?: throw HookNotFoundException(id)
    }

    override suspend fun createTriggersIfNotExist(
        deviceId: String,
        hookIds: List<String>,
        reportTypes: List<String>
    ): Flow<Trigger> {
        if (!getDevicePort.existsWithId(deviceId)) throw DeviceNotFoundException(deviceId)

        val existingTriggers = triggerPort.getTriggersByDeviceIdAndHookIds(deviceId, hookIds).toList()
        val newTriggers = reportTypes
            .flatMap { reportType -> hookIds.zip(List(hookIds.size) { reportType }) }
            .filterNot { hookIdAndReportType ->
                existingTriggers.any {
                    it.reportType == hookIdAndReportType.second && it.hookId == hookIdAndReportType.first
                }
            }.asFlow()
            .map { Trigger(null, "", deviceId, it.first, it.second) }

        return triggerPort.createTriggers(newTriggers)
    }
}