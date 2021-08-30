package com.test.student.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLogAspects {

    private static final Logger log = LoggerFactory.getLogger(ControllerLogAspects.class);

    @Before("execution(* com.test.student.controller.*Controller.*(..))")
    public void captureControllerRequest(JoinPoint joinPoint) {
        log.info("**** Request received for Class : " + joinPoint.getTarget().getClass().getSimpleName() + " of Method : " + joinPoint.getSignature().getName() + " ****");
    }

    @After("execution(* com.test.student.controller.*Controller.*(..))")
    public void captureControllerResponse(JoinPoint joinPoint) {
        log.info("**** Request completed for Class : " + joinPoint.getTarget().getClass().getSimpleName() + " of Method : " + joinPoint.getSignature().getName() + " ****");
    }

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object executionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object data;
        try {
            data = proceedingJoinPoint.proceed();
            long duration = System.currentTimeMillis() - start;
            log.info("**** Execution time to complete successful request : " + duration + " ms");
        } catch (Throwable throwable) {
            long duration = System.currentTimeMillis() - start;
            log.info("**** Execution time of failed request : " + duration + " ms");
            throw throwable;
        }
        return data;
    }
}
