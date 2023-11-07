package com.castielrdx.aquarius.req.cache.model

import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder

internal class InvocationTarget(
    private val targetClass: Class<*>,
    private val targetMethod: String,
    private val args: Array<Any>
) {
    override fun equals(other: Any?): Boolean {
        return EqualsBuilder.reflectionEquals(this, other)
    }

    override fun hashCode(): Int {
        return HashCodeBuilder.reflectionHashCode(this)
    }

    override fun toString(): String {
        return String.format(TO_STRING_TEMPLATE, targetClass.getName(), targetMethod, args.contentToString())
    }

    companion object {
        private const val TO_STRING_TEMPLATE = "%s.%s(%s)"
    }
}