package com.mibe.iot.thinker.device.domain.validation

import com.mibe.iot.thinker.device.domain.Device
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.pattern

const val DEVICE_NAME_LENGTH = 256
const val DEVICE_DESCRIPTION_LENGTH = 2048

val validateNewDevice = Validation<Device> {
    Device::name {
        maxLength(DEVICE_NAME_LENGTH) hint "validation.device.name.length"
    }
    Device::description required {
        maxLength(DEVICE_DESCRIPTION_LENGTH) hint "description can't be more than 2048 characters long"
    }
    Device::ip ifPresent {
        pattern("(?:[0-9]{1,3}\\.){3}[0-9]{1,3}") hint "validation.device.ip"
    }
}

val validateDevice = Validation<Device> {
    Device::id required {}
    run(validateNewDevice)
}
