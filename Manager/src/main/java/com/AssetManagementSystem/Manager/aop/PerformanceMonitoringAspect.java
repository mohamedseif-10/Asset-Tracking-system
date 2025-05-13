package com.AssetManagementSystem.Manager.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceMonitoringAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceMonitoringAspect.class);

    @Pointcut("execution(* com.AssetManagementSystem.Manager.service.*.*(..))")
    public void serviceMethods() {
    }

    @Around("serviceMethods()")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        String methodName = joinPoint.getSignature().getName();
        if (executionTime > 500) {
            logger.warn("Method {} took {} ms to execute, which is longer than expected.", methodName, executionTime);
        } else {
            logger.info("Method {} executed in {} ms.", methodName, executionTime);
        }

        return result;
    }
}