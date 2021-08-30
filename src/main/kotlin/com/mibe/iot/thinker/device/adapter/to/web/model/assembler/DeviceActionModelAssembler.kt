package com.mibe.iot.thinker.device.adapter.to.web.model.assembler

import com.mibe.iot.thinker.device.adapter.to.web.model.DeviceActionModel
import com.mibe.iot.thinker.device.domain.DeviceAction
import org.springframework.hateoas.server.reactive.ReactiveRepresentationModelAssembler
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class DeviceActionModelAssembler() : ReactiveRepresentationModelAssembler<DeviceAction, DeviceActionModel> {

    override fun toModel(domain: DeviceAction, exchange: ServerWebExchange): Mono<DeviceActionModel> {
        return domain.toModel().apply {
            add()
        }.toMono()
    }
}

fun DeviceAction.toModel() = DeviceActionModel(
    name,
    mapping,
    descriptionKey
)
