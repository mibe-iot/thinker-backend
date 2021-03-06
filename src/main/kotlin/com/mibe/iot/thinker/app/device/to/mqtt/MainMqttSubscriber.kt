package com.mibe.iot.thinker.app.device.to.mqtt

import com.fasterxml.jackson.databind.ObjectMapper
import com.hivemq.client.mqtt.datatypes.MqttQos.AT_LEAST_ONCE
import com.hivemq.client.mqtt.datatypes.MqttTopic
import com.mibe.iot.thinker.PROFILE_DEFAULT
import com.mibe.iot.thinker.PROFILE_PROD
import com.mibe.iot.thinker.domain.device.DeviceAction
import com.mibe.iot.thinker.domain.device.DeviceReport
import com.mibe.iot.thinker.domain.device.DeviceUpdates
import com.mibe.iot.thinker.service.device.SaveDeviceReportUseCase
import com.mibe.iot.thinker.service.device.UpdateDeviceUseCase
import com.mibe.iot.thinker.service.device.exception.DeviceNotFoundException
import com.mibe.iot.thinker.service.hooks.TriggerUseCase
import de.smartsquare.starter.mqtt.MqttSubscribe
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile(PROFILE_DEFAULT, PROFILE_PROD)
@Component
class MainMqttSubscriber
@Autowired constructor(
    private val updateDeviceUseCase: UpdateDeviceUseCase,
    private val saveDeviceReportUseCase: SaveDeviceReportUseCase,
    private val triggerUseCase: TriggerUseCase,
    private val jsonMapper: ObjectMapper
) {
    private val log = KotlinLogging.logger {}

    @MqttSubscribe(topic = "/mibe/actions", qos = AT_LEAST_ONCE)
    fun handleActionsSharing(actionsJson: String, topic: MqttTopic) {
        val deviceActionsData = jsonMapper.readValue(actionsJson, DeviceActionsDataModel::class.java)
        val actions = deviceActionsData.actions.map { DeviceAction(it.name) }.toSet()
        log.info { "Got actions from topic $topic; Actions: $actions; Device actions data: $deviceActionsData" }
        runBlocking {
            try {
                updateDeviceUseCase.updateDeviceAdditionalData(deviceActionsData.toDeviceUpdates())
            } catch (e: DeviceNotFoundException) {
                log.debug { "Got unknown device actions: $actions" }
            }
        }
    }

    @MqttSubscribe(topic = "/mibe/reports/+", qos = AT_LEAST_ONCE)
    fun handleReportsSharing(reportModelJson: String, topic: MqttTopic) {
        val reportModel = jsonMapper.readValue(reportModelJson, DeviceReportModel::class.java)
        log.debug { "Got report from topic $topic" }
        val deviceReport = DeviceReport(
            id = null,
            deviceId = topic.levels.last(),
            reportType = reportModel.reportType,
            reportData = reportModel.reportData
        )
        log.debug { "report: $deviceReport" }
        runBlocking {
            try {
                saveDeviceReportUseCase.saveReport(deviceReport)
                triggerUseCase.executeHookForReport(deviceReport)
            } catch (e: DeviceNotFoundException) {
                log.debug { "Got unknown device report: $deviceReport" }
            }
        }
    }

    fun DeviceActionsDataModel.toDeviceUpdates() = DeviceUpdates(
        id = deviceId,
        deviceClass = deviceClass,
        actions = actions.map { DeviceAction(it.name) }.toSet(),
        reportTypes = HashSet(reportTypes),
    )

}