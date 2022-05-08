package com.mibe.iot.thinker.service.hooks

import com.mibe.iot.thinker.domain.hooks.Hook
import kotlin.reflect.KClass

interface HookExecutor {

    abstract val hookType: KClass<out Hook>

    abstract fun executeHook(hook: Hook)

}