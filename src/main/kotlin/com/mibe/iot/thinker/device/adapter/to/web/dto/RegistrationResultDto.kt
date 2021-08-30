package com.mibe.iot.thinker.device.adapter.to.web.dto

import com.mibe.iot.thinker.device.domain.Device

data class RegistrationResultDto(
    val registeredDevice: Device,
    val token: String = "[Not implemented yet]"
)
