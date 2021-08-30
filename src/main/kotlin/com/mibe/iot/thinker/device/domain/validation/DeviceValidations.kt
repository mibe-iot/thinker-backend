package com.mibe.iot.thinker.device.domain.validation

import com.mibe.iot.thinker.device.domain.Device
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength

const val DEVICE_NAME_MIN_LENGTH = 4
const val DEVICE_NAME_LENGTH = 256
const val DEVICE_DESCRIPTION_LENGTH = 2048

val validateNewDevice = Validation<Device> {
    Device::name {
        minLength(DEVICE_NAME_MIN_LENGTH) hint "validation.device.name.min.length|||$DEVICE_NAME_MIN_LENGTH"
        maxLength(DEVICE_NAME_LENGTH) hint "validation.device.name.length|||$DEVICE_NAME_LENGTH"
    }
    Device::description required {
        maxLength(DEVICE_DESCRIPTION_LENGTH) hint "validation.device.description.length|||$DEVICE_DESCRIPTION_LENGTH"
    }
    Device::ip ifPresent {
    }
}

val validateDevice = Validation<Device> {
    Device::id required {}
    run(validateNewDevice)
}
