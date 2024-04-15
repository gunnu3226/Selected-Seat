package io.nbc.selectedseat.redis.distributedlock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionForLock {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Object proceed(
        final ProceedingJoinPoint proceedingJoinPoint
    ) throws Throwable {
        return proceedingJoinPoint.proceed();
    }
}
