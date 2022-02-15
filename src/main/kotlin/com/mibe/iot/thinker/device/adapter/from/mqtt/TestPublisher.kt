package com.mibe.iot.thinker.device.adapter.from.mqtt

import com.fasterxml.jackson.databind.ObjectMapper
import com.hivemq.client.mqtt.datatypes.MqttQos.AT_LEAST_ONCE
import com.mibe.iot.thinker.device.domain.DeviceAction
import com.mibe.iot.thinker.device.domain.DeviceActions
import de.smartsquare.starter.mqtt.Mqtt3Publisher
import org.springframework.stereotype.Component

@Component
class TestPublisher(private val mqttPublisher: Mqtt3Publisher, private val objectMapper: ObjectMapper) {

    fun publish() {
        val actions = listOf(DeviceAction("name", "mapping", "description"))
        mqttPublisher.publish("/mibe/humidity/actions", AT_LEAST_ONCE, objectMapper.writeValueAsString(DeviceActions(actions)))
    }

    class TemperaturePayload(val value: Int)
}