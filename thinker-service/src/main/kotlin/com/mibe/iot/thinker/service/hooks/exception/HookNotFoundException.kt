package com.mibe.iot.thinker.service.hooks.exception

class HookNotFoundException(val hookId: String) : Exception("Hook not found, hookId=$hookId")