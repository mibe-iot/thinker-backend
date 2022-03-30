package com.mibe.iot.thinker.app.device.to.mqtt

import com.fasterxml.jackson.databind.ObjectMapper
import com.hivemq.client.mqtt.datatypes.MqttQos.AT_LEAST_ONCE
import com.hivemq.client.mqtt.datatypes.MqttTopic
import com.mibe.iot.thinker.domain.device.DeviceAction
import com.mibe.iot.thinker.service.device.SaveDeviceReportUseCase
import com.mibe.iot.thinker.service.device.UpdateDeviceUseCase
import de.smartsquare.starter.mqtt.MqttSubscribe
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MainMqttSubscriber
@Autowired constructor(
    private val updateDeviceUseCase: UpdateDeviceUseCase,
    private val saveDeviceReportUseCase: SaveDeviceReportUseCase,
    private val jsonMapper: ObjectMapper
) {
    private val log = KotlinLogging.logger {}

    @MqttSubscribe(topic = "/mibe/actions", qos = AT_LEAST_ONCE)
    fun handleActionsSharing(actionsJson: String, topic: MqttTopic) {
        val deviceActionsData = jsonMapper.readValue(actionsJson, DeviceActionsDataModel::class.java)
        val actions = deviceActionsData.actions.map { DeviceAction(it.name) }.toSet()
        log.info { "Got actions from topic $topic" }
        log.info { "Actions: $actions" }
        runBlocking {
            updateDeviceUseCase.updateDeviceActionsAndClass(
                deviceActionsData.deviceId,
                deviceActionsData.deviceClass,
                actions
            )
        }
    }

    @MqttSubscribe(topic = "/mibe/reports/+", qos = AT_LEAST_ONCE)
    fun handleReportsSharing(reportModelJson: String, topic: MqttTopic) {
        val reportModel = jsonMapper.readValue(reportModelJson, DeviceReportModel::class.java)
        log.info { "Got report from topic $topic" }
        runBlocking { saveDeviceReportUseCase.saveReport(topic.levels.last(), reportModel.reportData) }
    }


}