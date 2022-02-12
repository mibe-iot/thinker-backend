package com.mibe.iot.thinker.discovery.application

const val DEVICE_NAME_LENGTH = 8

fun randomString(length: Int): String {
    val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return List(length) { charset.random() }
        .joinToString("")
}