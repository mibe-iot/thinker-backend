package com.mibe.iot.thinker.app.dev

import com.mibe.iot.thinker.PROFILE_DEV
import de.smartsquare.starter.mqtt.MqttAutoConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@EnableAutoConfiguration(exclude = [MqttAutoConfiguration::class])
@Profile(PROFILE_DEV)
@Configuration
class DevConfiguration {
}