package com.mibe.iot.thinker.discovery.domain.validation

import io.konform.validation.Validation
import io.konform.validation.jsonschema.pattern

val validateAddress = Validation<String> {
    pattern("([0-9a-fA-F]{2}:){5}[0-9a-fA-F]{2}") hint "validation.discovery.address"
}
