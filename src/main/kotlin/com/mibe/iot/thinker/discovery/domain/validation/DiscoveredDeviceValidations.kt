package com.mibe.iot.thinker.discovery.domain.validation

import com.mibe.iot.thinker.domain.MAC_ADDRESS_PATTERN
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.pattern

val validateAddress = Validation<String> {
    pattern(MAC_ADDRESS_PATTERN) hint "validation.discovery.address"
    maxLength(17) hint "validation.discovery.address.length"
    minLength(17) hint "validation.discovery.address.length"
}
