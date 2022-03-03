package com.mibe.iot.thinker.app.discovery.exception

class DiscoveredDeviceNotFoundException(val address: String) : Exception("Device with address=$address wasn't found")