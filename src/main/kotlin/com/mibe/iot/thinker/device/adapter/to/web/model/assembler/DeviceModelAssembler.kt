package com.mibe.iot.thinker.device.adapter.to.web.model.assembler

import com.mibe.iot.thinker.device.adapter.to.web.DeviceController
import com.mibe.iot.thinker.device.adapter.to.web.dto.DeviceDto
import com.mibe.iot.thinker.device.adapter.to.web.model.DeviceModel
import com.mibe.iot.thinker.device.adapter.to.web.model.assembler.exception.InvalidDeviceIdException
import com.mibe.iot.thinker.device.domain.Device
import com.mibe.iot.thinker.device.domain.DeviceAction
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.server.mvc.afford
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.hateoas.server.reactive.ReactiveRepresentationModelAssembler
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

/**
 * Device model assembler. Assembles HATEOAS model of the Device.
 *
 * @constructor Create Device model assembler
 */
@Component
class DeviceModelAssembler() : ReactiveRepresentationModelAssembler<Device, DeviceModel> {

    override fun toModel(domain: Device, exchange: ServerWebExchange): Mono<DeviceModel> {
        return domain.toModel().apply {
            add(
                linkTo<DeviceController> { getDevice(id, exchange) }.addAffordances(
                    mutableListOf(
                        afford<DeviceController> {
                            updateDevice(
                                id,
                                DeviceDto("id", "name", "description", "MAC", setOf()).toMono(),
                                exchange
                            )
                        },
                        afford<DeviceController> {
                            deleteDevice(id)
                        }
                    )
                ).withSelfRel()
            )
            add(linkTo<DeviceController> { getAllDevices(exchange) }.withRel(IanaLinkRelations.COLLECTION))
        }.toMono()
    }
}

fun Device.toModel() = DeviceModel(
    id ?: throw InvalidDeviceIdException(),
    name,
    description,
    address,
    actions = actions.map(DeviceAction::toModel).toSet()
)
