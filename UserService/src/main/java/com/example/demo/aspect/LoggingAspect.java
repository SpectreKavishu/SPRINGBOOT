package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    @Before("execution(* com.example.demo.service.UserService.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Executing: " + joinPoint.getSignature());
    }

    @After("execution(* com.example.demo.service.UserService.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("Executed: " + joinPoint.getSignature());
    }
}

