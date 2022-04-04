package com.mibe.iot.thinker.app.validation

import com.mibe.iot.thinker.domain.device.DeviceUpdates
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minItems
import io.konform.validation.jsonschema.minLength

const val DEVICE_NAME_MIN_LENGTH = 4
const val DEVICE_NAME_LENGTH = 256
const val DEVICE_DESCRIPTION_LENGTH = 2048
const val DEVICE_CLASS_MAX_LENGTH = 256
const val DEVICE_REPORT_TYPES_MIN_LENGTH = 1

val validateDeviceUpdates = Validation<DeviceUpdates> {
    DeviceUpdates::name ifPresent  {
        minLength(DEVICE_NAME_MIN_LENGTH) hint "validation.device.name.min.length|||$DEVICE_NAME_MIN_LENGTH"
        maxLength(DEVICE_NAME_LENGTH) hint "validation.device.name.length|||$DEVICE_NAME_LENGTH"
    }
    DeviceUpdates::description ifPresent {
        maxLength(DEVICE_DESCRIPTION_LENGTH) hint "validation.device.description.length|||$DEVICE_DESCRIPTION_LENGTH"
    }
    DeviceUpdates::deviceClass ifPresent {
        maxLength(DEVICE_CLASS_MAX_LENGTH) hint "validation.device.deviceClass.length|||$DEVICE_CLASS_MAX_LENGTH"
    }
    DeviceUpdates::reportTypes ifPresent {
        minItems(DEVICE_REPORT_TYPES_MIN_LENGTH) hint "validation.device.reportTypes.size|||$DEVICE_REPORT_TYPES_MIN_LENGTH"
    }
}
