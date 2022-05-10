package com.mibe.iot.thinker.app.hooks.from.persistence

import com.mibe.iot.thinker.domain.hooks.Trigger
import com.mibe.iot.thinker.persistence.repository.SpringDataTriggerRepository
import com.mibe.iot.thinker.service.hooks.port.TriggerPort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.asFlux
import org.springframework.stereotype.Component

@Component
class TriggerAdapter(
    private val triggerRepository: SpringDataTriggerRepository
): TriggerPort {

    override fun getTriggersByDeviceIdAndHookIds(deviceId: String, hookIds: List<String>): Flow<Trigger> {
        return triggerRepository.findAllByDeviceIdAndHookIdIn(deviceId, hookIds).asFlow()
            .map{ it.toTrigger() }
    }

    override fun createTriggers(triggers: Flow<Trigger>): Flow<Trigger> {
        return triggerRepository.saveAll(triggers.map{ it.toEntity() }.asFlux())
            .asFlow()
            .map { it.toTrigger() }
    }
}