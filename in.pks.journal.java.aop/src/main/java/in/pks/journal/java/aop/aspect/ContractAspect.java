package in.pks.journal.java.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ContractAspect {

    @Before("execution(* in.pks.journal.java.aop.util.SimpleUtil.*(..))")
    public void validate(JoinPoint joinPoint){
        System.out.println("AOP invoked");
        Object[] arguments = joinPoint.getArgs();

    }
}
