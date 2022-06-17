package com.mibe.iot.thinker.app

import com.mibe.iot.thinker.persistence.PersistenceBeanScanMarker
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableReactiveMongoRepositories(basePackageClasses = [PersistenceBeanScanMarker::class])
class ThinkerApplication

fun main(args: Array<String>) {
    runApplication<ThinkerApplication>(*args)
}
