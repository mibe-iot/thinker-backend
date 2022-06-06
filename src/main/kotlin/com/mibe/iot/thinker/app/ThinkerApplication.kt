package com.mibe.iot.thinker.app

import com.mibe.iot.thinker.domain.data.EmailAddress
import com.mibe.iot.thinker.domain.hooks.Hook
import com.mibe.iot.thinker.domain.hooks.SendEmailHook
import com.mibe.iot.thinker.persistence.PersistenceBeanScanMarker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement
import kotlin.reflect.KClass

@SpringBootApplication
@EnableScheduling
@EnableReactiveMongoRepositories(basePackageClasses = [PersistenceBeanScanMarker::class])
class ThinkerApplication {

    @Autowired
    lateinit var aaa: Environment

    @Bean
    fun cmd() = CommandLineRunner {
        println(aaa.getProperty("MONGO_INITDB_ROOT_USERNAME"))
    }
}

fun main(args: Array<String>) {
    runApplication<ThinkerApplication>(*args)
}
