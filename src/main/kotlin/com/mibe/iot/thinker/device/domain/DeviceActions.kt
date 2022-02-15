package com.mibe.iot.thinker.device.domain

import com.fasterxml.jackson.annotation.JsonUnwrapped

data class DeviceActions(
    val actions: List<DeviceAction>
)
