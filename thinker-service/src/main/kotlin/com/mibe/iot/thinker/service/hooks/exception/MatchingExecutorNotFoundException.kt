package com.mibe.iot.thinker.service.hooks.exception

import com.mibe.iot.thinker.domain.hooks.Hook
import kotlin.reflect.KClass

class MatchingExecutorNotFoundException(val hookType: KClass<out Hook>) :
    Exception("Cannot find executor for type=${hookType.simpleName}")