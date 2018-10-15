package com.zlikun.jee;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zlikun
 * @date 2018-10-15 11:08
 */
@Slf4j
public class CompletableFutureTest {

    private MockService mockService = new MockService();

    @Test
    public void test() throws ExecutionException, InterruptedException {

        // 使用一个线程池实现并发操作
        ExecutorService exec = Executors.newFixedThreadPool(4);

        long begin = System.currentTimeMillis();
        log.info("start: {}", begin);

        CompletableFuture[] futures = new CompletableFuture[2];
        AtomicReference<String> resultA = new AtomicReference<>();
        futures[0] = CompletableFuture.runAsync(() -> resultA.set(mockService.doAction1("X")), exec);
        AtomicReference<String> resultB = new AtomicReference<>();
        futures[1] = CompletableFuture.runAsync(() -> resultB.set(mockService.doAction2("Y")), exec);

        CompletableFuture allDoneFutures = CompletableFuture.allOf(futures);
        Object result = allDoneFutures.get();
        log.info("all done futures: {}", result);
        // 打印任务执行结果
        log.info("{} -> {}", System.currentTimeMillis(), resultA);
        log.info("{} -> {}", System.currentTimeMillis(), resultB);

        long end = System.currentTimeMillis();
        // end: 1539572829124, elapse: 247
        log.info("end: {}, elapse: {}", end, end - begin);

    }

}
