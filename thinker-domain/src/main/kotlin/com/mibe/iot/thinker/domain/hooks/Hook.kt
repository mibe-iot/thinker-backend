package com.mibe.iot.thinker.domain.hooks

/**
 * Base hook class.
 */
abstract class Hook(
    val id: String?,
    val name: String,
    val description: String
)