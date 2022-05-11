package com.mibe.iot.thinker.app.hooks

import com.mibe.iot.thinker.domain.device.DeviceReport
import com.mibe.iot.thinker.domain.hooks.Trigger
import com.mibe.iot.thinker.service.device.exception.DeviceNotFoundException
import com.mibe.iot.thinker.service.device.port.GetDevicePort
import com.mibe.iot.thinker.service.hooks.HookExecutorUseCase
import com.mibe.iot.thinker.service.hooks.TriggerUseCase
import com.mibe.iot.thinker.service.hooks.port.TriggerPort
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class TriggerService(
    private val getDevicePort: GetDevicePort,
    private val triggerPort: TriggerPort,
    private val hookExecutorUseCase: HookExecutorUseCase
) : TriggerUseCase {

    override suspend fun executeHookForReport(deviceReport: DeviceReport) {
        val trigger = triggerPort.getByDeviceIdAndReportType(
            deviceId = deviceReport.deviceId,
            reportType = deviceReport.reportType
        ) ?: return
        hookExecutorUseCase.executeHookById(trigger.hookId, deviceReport)
    }

    override suspend fun getAllTriggers(): Flow<Trigger> {
        return triggerPort.getAllTriggers()
    }

    override suspend fun getAllDeviceTriggers(deviceId: String): Flow<Trigger> {
        if (!getDevicePort.existsWithId(deviceId)) throw DeviceNotFoundException(deviceId)

        return triggerPort.getAllDeviceTriggers(deviceId)
    }
}