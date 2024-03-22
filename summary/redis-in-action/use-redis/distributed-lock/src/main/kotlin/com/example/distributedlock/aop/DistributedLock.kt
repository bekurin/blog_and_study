package com.example.distributedlock.aop

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FUNCTION

@Retention(RUNTIME)
@Target(FUNCTION)
annotation class DistributedLock(
    val key: String,
    val expireSeconds: Long
)
