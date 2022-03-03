package com.mibe.iot.thinker.app.device.adapter.from.mqtt

import com.fasterxml.jackson.databind.ObjectMapper
import de.smartsquare.starter.mqtt.Mqtt3Publisher
import org.springframework.stereotype.Component

@Component
class TestPublisher(private val mqttPublisher: Mqtt3Publisher, private val objectMapper: ObjectMapper) {

    fun publish() {
//        val actions = listOf(DeviceAction("name", "mapping", "description"))
//        mqttPublisher.publish("/mibe/humidity/actions", AT_LEAST_ONCE, objectMapper.writeValueAsString(DeviceActions(actions)))
    }

    class TemperaturePayload(val value: Int)
}