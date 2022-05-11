package com.mibe.iot.thinker.domain.hooks

class Trigger(
    var id: String?,
    val name: String,
    val deviceId: String,
    val hookId: String,
    val reportType: String
)