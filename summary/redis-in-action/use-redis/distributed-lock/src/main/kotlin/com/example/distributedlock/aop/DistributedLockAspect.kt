package com.example.distributedlock.aop

import com.example.distributedlock.DistributedLockService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class DistributedLockAspect(
    private val distributedLockService: DistributedLockService
) {
    private val log: Logger = LoggerFactory.getLogger(DistributedLockAspect::class.java)

    @Around("@annotation(DistributedLock)")
    fun doDistributedLock(joinPoint: ProceedingJoinPoint, name: String, distributedLock: DistributedLock): Any? {
        val isAcquireLock = distributedLockService.acquireLock(distributedLock.key, distributedLock.expireSeconds)
        if (isAcquireLock.not()) {
            log.info("fail to get lock... do nothing")
            return null
        }
        val result = joinPoint.proceed()
        distributedLockService.leaseLock(distributedLock.key)
        return result
    }
}
