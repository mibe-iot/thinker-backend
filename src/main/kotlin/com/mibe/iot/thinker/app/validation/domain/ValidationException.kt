package com.mibe.iot.thinker.app.validation.domain

import io.konform.validation.ValidationErrors

class ValidationException(val errors: ValidationErrors) : Exception()
