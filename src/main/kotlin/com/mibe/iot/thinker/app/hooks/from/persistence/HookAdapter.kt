package com.mibe.iot.thinker.app.hooks.from.persistence

import com.mibe.iot.thinker.domain.hooks.Hook
import com.mibe.iot.thinker.service.hooks.port.HookPort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import mu.KotlinLogging
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component

@Component
class HookAdapter(
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) : HookPort {
    val log = KotlinLogging.logger {}

    override suspend fun getHookById(id: String): Hook? {
        return reactiveMongoTemplate.find(Query.query(Criteria.where("id").`is`(id)), Hook::class.java, "hooks")
            .awaitFirstOrNull()
    }

    override suspend fun getAllHooks(): Flow<Hook> {
        return reactiveMongoTemplate.findAll(Hook::class.java, "hooks").asFlow()
    }

    override suspend fun createHook(hook: Hook) {
        hook.id = null
        reactiveMongoTemplate.save(hook, "hooks").awaitFirstOrNull()
    }

    override suspend fun deleteHookById(id: String) {
        reactiveMongoTemplate.remove(
            Query.query(Criteria.where("id").`is`(id)),
            Hook::class.java, "hooks"
        ).awaitFirstOrNull()
    }
}