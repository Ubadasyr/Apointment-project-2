package com.servicesproject.project.DTO;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

import java.util.Map;


@Aspect
@Component
public class Aop {

    @Around("execution(* com.servicesproject.project.services.*.*(..))")
    public Object handleExceptions(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            return joinPoint.proceed();
        } catch (RuntimeException ex) {

            return ResponseEntity
                    .status(400)
                    .body(Map.of(
                            "errors", ex.getMessage()

                    ));

        }catch (Exception ex) {
            return ResponseEntity
                    .status(500)
                    .body(Map.of(
                            "error", "Internal server error"

                    ));
        }



    }

    private static final Logger logger = (Logger) LoggerFactory.getLogger(Aop.class);

    @Around("execution(* com.servicesproject.project.services.*.*(..))")
    public Object logMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        logger.info("Starting method__: {} with args: {}", methodName, args);

        Object result;
        try {
            result = joinPoint.proceed();
        } finally {
            logger.info("Finished method: {}", methodName);
        }

        return result;
    }


}
