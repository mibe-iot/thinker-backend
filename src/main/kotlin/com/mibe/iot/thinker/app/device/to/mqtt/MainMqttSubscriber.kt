package com.mibe.iot.thinker.app.device.to.mqtt

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.hivemq.client.mqtt.datatypes.MqttQos.AT_LEAST_ONCE
import com.hivemq.client.mqtt.datatypes.MqttTopic
import com.mibe.iot.thinker.domain.device.DeviceAction
import com.mibe.iot.thinker.domain.device.DeviceUpdates
import com.mibe.iot.thinker.service.device.UpdateDeviceUseCase
import de.smartsquare.starter.mqtt.MqttSubscribe
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MainMqttSubscriber
@Autowired constructor(
    private val updateDeviceUseCase: UpdateDeviceUseCase,
    private val jsonMapper: ObjectMapper
) {

    @MqttSubscribe(topic = "/mibe/actions", qos = AT_LEAST_ONCE)
    fun handleActionsSharing(actionsJson: String, topic: MqttTopic) {
        val deviceActionsData = jsonMapper.readValue(actionsJson, DeviceActionsData::class.java)
        val actions = deviceActionsData.actions.map { it.withDeviceName(deviceActionsData.deviceName) }.toSet()
        val deviceUpdates = DeviceUpdates(id = deviceActionsData.deviceName, actions = actions)
        runBlocking { updateDeviceUseCase.updateDevice(deviceUpdates) }
    }

    private fun DeviceAction.withDeviceName(deviceName: String) = DeviceAction(
        name = name,
        deviceName = deviceName,
        descriptionKey = descriptionKey
    )

}