package com.mibe.iot.thinker.app.dev

import com.mibe.iot.thinker.PROFILE_DEFAULT
import com.mibe.iot.thinker.PROFILE_DEV
import com.mibe.iot.thinker.PROFILE_PROD
import com.mibe.iot.thinker.domain.device.DeviceAction
import com.mibe.iot.thinker.service.device.port.ControlDeviceActionPort
import mu.KotlinLogging
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile(PROFILE_DEV)
@Component
class DeviceActionsDispatcherMock : ControlDeviceActionPort {
    private val log = KotlinLogging.logger {}

    override fun activateAction(deviceId: String, action: DeviceAction) {
        log.warn { "dev mode: activateAction(deviceId = $deviceId, action = $action)" }
    }

    override fun activateActionReliably(deviceId: String, action: DeviceAction) {
        TODO("Not yet implemented")
    }
}