package com.mibe.iot.thinker.device.adapter.to.web.model.assembler

import com.mibe.iot.thinker.device.adapter.to.web.DeviceReportController
import com.mibe.iot.thinker.device.adapter.to.web.dto.DeviceReportDto
import com.mibe.iot.thinker.device.adapter.to.web.model.DeviceReportModel
import com.mibe.iot.thinker.device.adapter.to.web.model.assembler.exception.DeviceReportIdNotPresentException
import com.mibe.iot.thinker.device.domain.DeviceReport
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.hateoas.server.reactive.ReactiveRepresentationModelAssembler
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class DeviceReportModelAssembler : ReactiveRepresentationModelAssembler<DeviceReport, DeviceReportModel> {

    override fun toModel(domain: DeviceReport, exchange: ServerWebExchange): Mono<DeviceReportModel> {
        return domain.toModel().apply {
            add(
                linkTo<DeviceReportController> { saveReport("deviceID", DeviceReportDto(mapOf()).toMono(), exchange) }
                    .withRel(IanaLinkRelations.CREATE_FORM)
            )
        }.toMono()
    }
}

fun DeviceReport.toModel() = DeviceReportModel(
    id ?: throw DeviceReportIdNotPresentException(),
    deviceId,
    HashMap(reportData),
    dateTimeCreated,
)