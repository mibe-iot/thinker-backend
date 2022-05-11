package com.mibe.iot.thinker.service.hooks

import com.mibe.iot.thinker.domain.device.DeviceReport

interface HookExecutorUseCase {

    suspend fun executeHookById(hookId: String, report: DeviceReport)

}