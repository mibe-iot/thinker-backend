package com.mibe.iot.thinker.app.device.adapter.to.mqtt

import com.fasterxml.jackson.databind.ObjectMapper
import com.hivemq.client.mqtt.datatypes.MqttQos.AT_LEAST_ONCE
import com.hivemq.client.mqtt.datatypes.MqttTopic
import com.mibe.iot.thinker.app.device.adapter.from.mqtt.TestPublisher
import de.smartsquare.starter.mqtt.MqttSubscribe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MainMqttSubscriber
@Autowired constructor(
    private val jsonObjectMapper: ObjectMapper
) {

    @MqttSubscribe(topic = "/home/temperature", qos = AT_LEAST_ONCE)
    fun subscribe(payload: TestPublisher.TemperaturePayload, topic: MqttTopic) {
        println("Temperature is ${payload.value} °C in room ${topic.levels[1]}]")
    }

//    @MqttSubscribe(topic = "/mibe/+/actions", qos = AT_LEAST_ONCE)
//    fun handleActionsSharing(actionsJson: String, topic: MqttTopic) {
//        val actions = jsonObjectMapper.readValue(actionsJson, DeviceActions::class.java)
//        println("Received actions: $actions")
//    }

}