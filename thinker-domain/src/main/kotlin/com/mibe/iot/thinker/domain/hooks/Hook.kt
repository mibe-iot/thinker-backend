package com.mibe.iot.thinker.domain.hooks

/**
 * Base hook class.
 */
abstract class Hook(
    var id: String?,
    val name: String,
    val description: String,
    var type: String
)