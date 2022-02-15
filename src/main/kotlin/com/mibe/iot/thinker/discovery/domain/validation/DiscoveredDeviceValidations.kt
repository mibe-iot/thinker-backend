package com.mibe.iot.thinker.discovery.domain.validation

import com.mibe.iot.thinker.domain.MAC_ADDRESS_PATTERN
import io.konform.validation.Validation
import io.konform.validation.jsonschema.pattern

val validateAddress = Validation<String> {
    pattern(MAC_ADDRESS_PATTERN) hint "validation.discovery.address"
}
