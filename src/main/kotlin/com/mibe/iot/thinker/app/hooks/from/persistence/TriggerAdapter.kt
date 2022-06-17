package com.mibe.iot.thinker.app.hooks.from.persistence

import com.mibe.iot.thinker.domain.hooks.Trigger
import com.mibe.iot.thinker.persistence.repository.SpringDataTriggerRepository
import com.mibe.iot.thinker.service.hooks.port.TriggerPort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Component

@Component
class TriggerAdapter(
    private val triggerRepository: SpringDataTriggerRepository
) : TriggerPort {

    override fun getTriggersByDeviceIdAndHookIds(deviceId: String, hookIds: List<String>): Flow<Trigger> {
        return triggerRepository.findAllByDeviceIdAndHookIdIn(deviceId, hookIds).asFlow()
            .map { it.toTrigger() }
    }

    override suspend fun getByDeviceIdAndReportType(deviceId: String, reportType: String): Trigger? {
        return triggerRepository.findByDeviceIdAndReportType(deviceId, reportType)
            .map { it.toTrigger() }
            .awaitSingleOrNull()
    }

    override suspend fun getAllTriggers(): Flow<Trigger> {
        return triggerRepository.findAll().map { it.toTrigger() }.asFlow()
    }

    override suspend fun getAllDeviceTriggers(deviceId: String): Flow<Trigger> {
        return triggerRepository.findAllByDeviceId(deviceId).map { it.toTrigger() }.asFlow()
    }

    override fun createTriggers(triggers: Flow<Trigger>): Flow<Trigger> {
        return triggerRepository.saveAll(triggers.map { it.toEntity() }.asFlux())
            .asFlow()
            .map { it.toTrigger() }
    }

    override suspend fun deleteTriggerById(id: String) {
        triggerRepository.deleteById(id).awaitFirstOrNull()
    }

    override suspend fun deleteAllTriggersByHookId(hookId: String) {
        triggerRepository.deleteAllByHookId(hookId).awaitSingleOrNull()
    }

    override suspend fun deleteAllTriggersByDeviceId(deviceId: String) {
        triggerRepository.deleteAllByDeviceId(deviceId).awaitFirstOrNull()
    }
}