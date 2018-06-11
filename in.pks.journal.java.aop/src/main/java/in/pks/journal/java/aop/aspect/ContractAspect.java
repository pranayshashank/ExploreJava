package in.pks.journal.java.aop.aspect;

import in.pks.journal.java.aop.annotation.Constraint;
import in.pks.journal.java.aop.exception.ConstraintValidationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Objects;

@Component
@Aspect
public class ContractAspect {

    //@Before("execution(* in.pks.journal.java.aop.util.SimpleUtil.*(@in.pks.journal.java.aop.annotation.Constraint (*)))")
    @Before("execution(* in.pks.journal.java.aop.util.SimpleUtil.*(..))")
    public void validateConstraint(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Annotation[][] annotations2x2 = signature.getMethod().getParameterAnnotations();
        if (Objects.nonNull(annotations2x2) && annotations2x2.length > 0) {
            int index = 0;
            for (Annotation[] annotArray : annotations2x2) {
                if (Objects.nonNull(annotArray) && annotArray.length > 0) {
                    Annotation annotation = annotArray[0];
                    if (annotation.annotationType() == in.pks.journal.java.aop.annotation.Constraint.class) {
                        //System.out.println("Value: " + ((in.pks.journal.java.aop.annotation.Constraint)annotation).value());
                        String checkText = ((in.pks.journal.java.aop.annotation.Constraint) annotation).value();
                        Object obj = joinPoint.getArgs()[index];
                        String parameter = ((MethodSignature) joinPoint.getSignature()).getParameterNames()[index];
                        if ("NotNull".equalsIgnoreCase(checkText)) {
                            boolean valid = Objects.nonNull(obj);
                            System.out.println("Valid: " + valid);
                            if (!valid) {
                                String msg = "parameter '" + parameter + "' can't be null in method: " + signature.getMethod().toGenericString();
                                System.err.println("ConstraintValidationException: " + msg);
                                throw new ConstraintValidationException(msg);
                            }
                        }
                    }
                }
                ++index;
            }
        }
    }
}
