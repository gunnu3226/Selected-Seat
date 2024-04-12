package io.nbc.selectedseat.redis.distributedlock;

import io.nbc.selectedseat.redis.service.RedissonRedisService;
import java.lang.reflect.Method;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAop {

    private static final String LOCK_PREFIX = "SS_LOCK:";
    private final TransactionForLock transactionForLock;
    private final RedissonRedisService redissonRedisService;

    @Around("@annotation(io.nbc.selectedseat.redis.distributedlock.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(
            DistributedLock.class);

        String key = LOCK_PREFIX + CustomSpELParser.getDynamicValue(
            signature.getParameterNames(),
            joinPoint.getArgs(),
            distributedLock.key()
        );

        if (isReserved(key)) {
            throw new RuntimeException("이미 선점된 좌석입니다");
        }

        return transactionForLock.proceed(joinPoint);
    }

    private boolean isReserved(final String key) {
        return Boolean.FALSE.equals(
            redissonRedisService.setIfAbsent(key, true,
                Duration.ofSeconds(31 * 24 * 60 * 60))
        );
    }
}
