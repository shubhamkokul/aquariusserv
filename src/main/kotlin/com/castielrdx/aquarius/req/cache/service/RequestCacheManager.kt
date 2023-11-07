package com.castielrdx.aquarius.req.cache.service

import com.castielrdx.aquarius.req.cache.model.InvocationTarget
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Component
internal class RequestCacheManager {
    private val cache: MutableMap<InvocationTarget, Any> = ConcurrentHashMap()
    fun get(invocationContext: InvocationTarget): Optional<Any> {
         return Optional.ofNullable(cache[invocationContext])
    }

    fun put(methodInvocation: InvocationTarget, result: Any) {
         cache[methodInvocation] = result
    }
}