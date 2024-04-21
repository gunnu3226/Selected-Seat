package io.nbc.selectedseat.queue.service;

import java.time.Instant;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueueWriter {

    public static final String USER_WAIT_QUEUE = "user:queue:%s:wait";
    public static final String USER_WAIT_QUEUE_FOR_SCAN = "user:queue:*:wait";
    public static final String USER_PROCEED_QUEUE = "user:queue:%s:proceed";
    public static final Long ALLOW_MAX_QUEUE_SIZE = 10L;

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    public Mono<Long> registerToQueue(
        final String queue,
        final Long memberId
    ) {
        return reactiveRedisTemplate.opsForZSet()
            .add(USER_WAIT_QUEUE.formatted(queue), memberId.toString(),
                Instant.now().getEpochSecond())
            .filter(rs -> rs)
            .switchIfEmpty(Mono.error(new Exception("[ERROR]"))) // TODO: will be replace to custom exception
            .flatMap(rs -> reactiveRedisTemplate.opsForZSet()
                .rank(USER_WAIT_QUEUE.formatted(queue), memberId.toString()))
            .map(i -> i >= 0 ? i + 1 : i);
    }

    public Mono<Long> allowUser(final String queue) {
        return reactiveRedisTemplate.opsForZSet()
            .size(USER_PROCEED_QUEUE.formatted(queue))
            .flatMap(size -> Mono.just(ALLOW_MAX_QUEUE_SIZE - size))
            .flatMap(count -> count > 0
                ? reactiveRedisTemplate.opsForZSet()
                .popMin(USER_WAIT_QUEUE.formatted(queue), count)
                .flatMap(member -> reactiveRedisTemplate.opsForZSet()
                    .add(USER_PROCEED_QUEUE.formatted(queue),
                        Objects.requireNonNull(member.getValue()),
                        Instant.now().getEpochSecond()))
                .count()
                : Mono.just(0L)
            );
    }

    public Mono<Boolean> tryEnter(
        final String queue,
        final Long memberId
    ) {
        return isAllowedMember(queue, memberId)
            .filter(isAllow -> isAllow)
            .switchIfEmpty(
                registerToQueue(queue, memberId)
                    .onErrorResume(ex -> Mono.just(-1L))
                    .map(i -> false)
            );
    }

    public Mono<Boolean> isAllowedMember(
        final String queue,
        final Long userId
    ) {
        return reactiveRedisTemplate.opsForZSet()
            .rank(USER_PROCEED_QUEUE.formatted(queue), userId.toString())
            .defaultIfEmpty(-1L)
            .map(rank -> rank >= 0);
    }

    public Mono<Long> getRank(
        final String queue,
        final Long memberId
    ) {
        return reactiveRedisTemplate.opsForZSet()
            .rank(USER_WAIT_QUEUE.formatted(queue), memberId.toString())
            .defaultIfEmpty(-1L)
            .map(rank -> rank >= 0 ? rank + 1 : rank);
    }

    public Mono<Long> exitQueue(
        final String queue,
        final Long memberId
    ) {
        return reactiveRedisTemplate.opsForZSet()
            .remove(USER_PROCEED_QUEUE.formatted(queue), memberId.toString());
    }

    public Mono<Long> getQueueSize(final String queue) {
        return reactiveRedisTemplate.opsForZSet()
            .size(USER_WAIT_QUEUE.formatted(queue));
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 3000)
    public void scheduleAllowUser() {
        reactiveRedisTemplate.scan(ScanOptions.scanOptions()
                .match(USER_WAIT_QUEUE_FOR_SCAN)
                .count(100)
                .build())
            .map(key -> key.split(":")[2])
            .flatMap(queue -> allowUser(queue).map(
                allowed -> Tuples.of(queue, allowed)))
            .subscribe();
    }
}
