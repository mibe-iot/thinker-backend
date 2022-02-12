package com.mibe.iot.thinker.device.adapter.from.mqtt

import com.hivemq.client.mqtt.datatypes.MqttQos.AT_LEAST_ONCE
import de.smartsquare.starter.mqtt.Mqtt3Publisher
import org.springframework.stereotype.Component

@Component
class TestPublisher(private val mqttPublisher: Mqtt3Publisher) {

    fun publish(payload: TemperaturePayload) {
        mqttPublisher.publish("/home/temperature", AT_LEAST_ONCE, payload)
    }

    class TemperaturePayload(val value: Int)
}