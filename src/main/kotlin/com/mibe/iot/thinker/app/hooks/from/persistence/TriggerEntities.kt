package com.mibe.iot.thinker.app.hooks.from.persistence

import com.mibe.iot.thinker.domain.hooks.Trigger
import com.mibe.iot.thinker.persistence.entity.hooks.TriggerEntity

fun TriggerEntity.toTrigger() = Trigger(
    id = id,
    name = name,
    deviceId = deviceId,
    hookId = hookId,
    reportType = reportType
)

fun Trigger.toEntity() = TriggerEntity(
    id = id,
    name = name,
    deviceId = deviceId,
    hookId = hookId,
    reportType = reportType
)