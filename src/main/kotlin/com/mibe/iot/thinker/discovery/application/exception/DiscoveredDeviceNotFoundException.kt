package com.mibe.iot.thinker.discovery.application.exception

class DiscoveredDeviceNotFoundException(val address: String): Exception("Device with address=$address wasn't found")