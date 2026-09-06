package com.project.user_service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class ExecutionTimeAspect {
    @Pointcut("execution (* com.project.user_service.controller.*.*(..))")
    public void controllerMethods(){}

    @Around("controllerMethods()")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        try{
            return joinPoint.proceed();
        } finally {
            long end = System.nanoTime();
            long elapsed = end - start;
            long elapsedMs =  TimeUnit.NANOSECONDS.toMillis(elapsed);
            String signature = joinPoint.getSignature().toString();
            log.info("Controller method {} executed in {} ms", signature, elapsedMs);
        }
    }
}
