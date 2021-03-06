package com.mibe.iot.thinker.app.device.from.mqtt

import com.fasterxml.jackson.databind.ObjectMapper
import com.hivemq.client.mqtt.datatypes.MqttQos
import com.mibe.iot.thinker.PROFILE_DEFAULT
import com.mibe.iot.thinker.PROFILE_PROD
import com.mibe.iot.thinker.domain.device.DeviceAction
import com.mibe.iot.thinker.service.device.port.ControlDeviceActionPort
import de.smartsquare.starter.mqtt.Mqtt3Publisher
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile(PROFILE_DEFAULT, PROFILE_PROD)
@Component
class DeviceActionsDispatcher(
    private val mqttPublisher: Mqtt3Publisher,
    private val jsonMapper: ObjectMapper
) : ControlDeviceActionPort {

    override fun activateAction(deviceId: String, action: DeviceAction) =
        activateAction(deviceId, action, MqttQos.AT_LEAST_ONCE)

    override fun activateActionReliably(deviceId: String, action: DeviceAction) =
        activateAction(deviceId, action, MqttQos.EXACTLY_ONCE)

    private fun activateAction(deviceId: String, action: DeviceAction, qos: MqttQos) {
        mqttPublisher.publish(
            "/mibe/${deviceId}/${action.name}",
            qos,
            jsonMapper.writeValueAsString(ActionInvocation(action.name))
        )
    }

    data class ActionInvocation(val actionName: String)

}