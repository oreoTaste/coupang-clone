package com.quick.spring.project.aop;

import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Component
@Aspect
public class Logger {

    @Around("execution(* com.quick.spring.project..*(..))")
    public Object execution(ProceedingJoinPoint joinPoint) throws Throwable{
        // 접속자 IP 얻어오기
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        String ip = request.getRemoteAddr();

        long start = System.currentTimeMillis();
        try{
            return joinPoint.proceed();
        } finally {
            long last = System.currentTimeMillis();
            LogManager
                    .getLogger("(" + request.getRemoteAddr() + ") :: "
                        + joinPoint.getTarget().getClass().getSimpleName() + ">" + joinPoint.getSignature().getName())
                    .info(last - start + "ms spent");
        }
    }
}
