package com.mibe.iot.thinker.app.validation

import io.konform.validation.Validation
import io.konform.validation.jsonschema.pattern

val validateEmailAddress = Validation<String> {
    pattern("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,4})+\$")
}