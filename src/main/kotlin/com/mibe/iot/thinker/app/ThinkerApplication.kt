package com.mibe.iot.thinker.app

import com.mibe.iot.thinker.domain.data.EmailAddress
import com.mibe.iot.thinker.domain.hooks.Hook
import com.mibe.iot.thinker.domain.hooks.SendEmailHook
import com.mibe.iot.thinker.persistence.PersistenceBeanScanMarker
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.scheduling.annotation.EnableScheduling
import kotlin.reflect.KClass

@SpringBootApplication
@EnableScheduling
@EnableReactiveMongoRepositories(basePackageClasses = [PersistenceBeanScanMarker::class])
class ThinkerApplication

fun main(args: Array<String>) {
    val hook : Hook = SendEmailHook("1", "1", "1", EmailAddress("1"))
    val a: KClass<out Hook> = hook::class
    runApplication<ThinkerApplication>(*args)
}
