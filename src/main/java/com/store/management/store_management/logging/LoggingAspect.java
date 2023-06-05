package com.store.management.store_management.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Configuration
@EnableAspectJAutoProxy
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

     @Pointcut("execution(* com.store.management.store_management.service.StoreManagementService.*(..))")
     public void loggingServicePointCut(){}


     @Before(value = "loggingServicePointCut()")
     public void methodsCallAdvice(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object [] methodArguments = joinPoint.getArgs();

         /**
          * this wont be logged as info
          * in a prod environment
          */
         logger.info("Method {} was called with arguments: {}", methodName, methodArguments);
     }

}
