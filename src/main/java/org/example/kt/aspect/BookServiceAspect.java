package org.example.kt.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class BookServiceAspect {

    @Pointcut("execution(* org.example.kt.service..*(..))")
    public void serviceLayer() {
    }

    @Before("serviceLayer()")
    public void beforeMethod(JoinPoint joinPoint) {

        log.info("Method: {} called with args: {}",
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(
            pointcut = "serviceLayer()",
            returning = "result"
    )
    public void afterReturning(JoinPoint joinPoint, Object result) {

        log.info("Method: {} returned: {}",
                joinPoint.getSignature().getName(),
                result);
    }

    @AfterThrowing(
            pointcut = "serviceLayer()",
            throwing = "ex"
    )
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {

        log.error("Method: {} threw exception: {}",
                joinPoint.getSignature().getName(),
                ex.getMessage());
    }
}
