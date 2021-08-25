package com.mibe.iot.thinker.validation.domain

import io.konform.validation.ValidationErrors

class ValidationException(val errors: ValidationErrors) : Exception()
