package com.mibe.iot.thinker

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.MessageSource

@SpringBootTest
class ThinkerApplicationTests {

    @Autowired
    lateinit var messageSource: MessageSource

    @Test
    fun contextLoads() {
        println(messageSource.toString())
    }
}
