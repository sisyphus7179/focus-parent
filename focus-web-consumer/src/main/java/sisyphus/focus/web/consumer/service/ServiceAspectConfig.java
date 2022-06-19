package sisyphus.focus.web.consumer.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspectConfig {

    @Pointcut("execution(public * sisyphus.focus.web.consumer.service.impl.*.*(..))")
    public void serviceExpressionAccess() {
    }

    @Around("serviceExpressionAccess()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Service method start....................");
        try {
            return joinPoint.proceed();
        } catch (Throwable t) {
            throw t;
        } finally {
            System.out.println("Service method end....................");
        }
    }

}
