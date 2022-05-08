package com.mibe.iot.thinker.service.hooks.port

import com.mibe.iot.thinker.domain.hooks.Hook

interface HookPort {

    fun getHookById(id: String): Hook?

    fun createHook(hook: Hook)

}