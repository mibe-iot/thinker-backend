package com.mibe.iot.thinker.app.discovery.adapter.from.ble

import java.util.*

const val BIT_SERVICE = "29d9d932-8a5f-11ec-a8a3-0242ac120002"
const val BIT_CHARACTERISTIC_NAME = "70f3674a-8a62-11ec-a8a3-0242ac120002"
const val BIT_CHARACTERISTIC_SSID = "63be641a-8a5f-11ec-a8a3-0242ac120002"
const val BIT_CHARACTERISTIC_PASSWORD = "7385dab8-8a5f-11ec-a8a3-0242ac120002"

val allowedUUIDs = listOf(
    BIT_SERVICE
).map(UUID::fromString)
