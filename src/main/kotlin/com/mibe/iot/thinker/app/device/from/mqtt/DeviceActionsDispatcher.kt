package com.mibe.iot.thinker.app.device.from.mqtt

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.hivemq.client.mqtt.datatypes.MqttQos
import com.mibe.iot.thinker.domain.device.DeviceAction
import com.mibe.iot.thinker.service.device.port.ControlDeviceActionPort
import de.smartsquare.starter.mqtt.Mqtt3Publisher
import org.springframework.stereotype.Component

@Component
class DeviceActionsDispatcher(
    private val mqttPublisher: Mqtt3Publisher,
    private val jsonMapper: ObjectMapper
) : ControlDeviceActionPort {

    override fun activateAction(deviceName: String, action: DeviceAction) {
        mqttPublisher.publish("/mibe/${action}",
            MqttQos.AT_LEAST_ONCE,
            jsonMapper.writeValueAsString(ActionInvocation(action.name)))
    }

    data class ActionInvocation(val actionName: String)

}