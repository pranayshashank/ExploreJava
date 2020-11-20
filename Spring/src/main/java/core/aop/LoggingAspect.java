package core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    @Pointcut("execution(public * core.beans.*Display.*(..))")
    private void anyDisplayUnitMethod() {}

    @Before("anyDisplayUnitMethod()")
    public void logBeforeMethodEnter(JoinPoint joinPoint) {
        System.out.println(String.format("\tEntering into: %s::%s",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName()));
    }

    @After("anyDisplayUnitMethod()")
    public void logOnMethodReturn(JoinPoint joinPoint) {
        System.out.println(String.format("\tReturning from: %s::%s",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName()));
    }
}
