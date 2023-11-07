package com.castielrdx.aquarius.req.cache.service

import com.castielrdx.aquarius.req.cache.model.InvocationTarget
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Aspect
@Component
internal class MemoizeAspect @Autowired constructor(private val requestCacheManager: RequestCacheManager) {
    @Around("@annotation(com.castielrdx.aquarius.req.cache.annotation.Memoize)")
    @Throws(Throwable::class)
    fun processMemoize(pjp: ProceedingJoinPoint): Any {
        val invocationTarget = InvocationTarget(
            pjp.signature.declaringType,
            pjp.signature.name,
            pjp.args
        )
        val cachedResult = requestCacheManager.get(invocationTarget)
        return if (cachedResult.isPresent) {
            val result = cachedResult.get()
            LOGGER.info("Using cached value {}, for invocation: {}", result, invocationTarget)
            result
        } else {
            val methodResult = pjp.proceed()
            LOGGER.info(
                "Caching result: {}, for invocation: {}",
                methodResult,
                invocationTarget
            )
            requestCacheManager.put(invocationTarget, methodResult)
            methodResult
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(MemoizeAspect::class.java)
    }
}