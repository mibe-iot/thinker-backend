package com.mibe.iot.thinker.app.hooks.to.web

import com.mibe.iot.thinker.app.hooks.to.web.model.DeviceHooksAndReportTypesModel
import com.mibe.iot.thinker.domain.hooks.Trigger
import com.mibe.iot.thinker.service.hooks.HookUseCase
import com.mibe.iot.thinker.service.hooks.TriggerUseCase
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/triggers")
class TriggersController(
    private val hookUseCase: HookUseCase,
    private val triggerUseCase: TriggerUseCase
) {

    @PostMapping("/{deviceId}")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createTriggers(
        @PathVariable deviceId: String,
        @RequestBody model: DeviceHooksAndReportTypesModel
    ): Flow<Trigger> {
        return hookUseCase.createTriggersIfNotExist(
            deviceId = deviceId,
            hookIds = model.hookIds,
            reportTypes = model.reportTypes
        )
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getAllTriggers(): Flow<Trigger> = triggerUseCase.getAllTriggers()

    @GetMapping("/{deviceId}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getAllTriggers(@PathVariable deviceId: String): Flow<Trigger> =
        triggerUseCase.getAllDeviceTriggers(deviceId)

}