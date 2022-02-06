package com.mibe.iot.thinker

import com.mibe.iot.thinker.device.adapter.from.persistance.SpringDataDeviceRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableReactiveMongoRepositories(basePackageClasses = [SpringDataDeviceRepository::class])
class ThinkerApplication

fun main(args: Array<String>) {
    runApplication<ThinkerApplication>(*args)
}
