package com.zlikun.jee;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zlikun
 * @date 2018-10-15 10:32
 */
@Slf4j
public class CountDownLatchTest {

    private MockService mockService = new MockService();

    @Test
    public void test() {

        // 使用一个线程池实现并发操作
        ExecutorService exec = Executors.newFixedThreadPool(4);

        long begin = System.currentTimeMillis();
        log.info("start: {}", begin);

        // 使用 CountDownLatch 来判断一个事务是否执行完成
        final CountDownLatch latch = new CountDownLatch(2);

        // 异步执行业务逻辑
        AtomicReference<String> resultA = new AtomicReference<>();
        exec.execute(() -> {
            resultA.set(mockService.doAction1("X"));
            latch.countDown();
        });
        AtomicReference<String> resultB = new AtomicReference<>();
        exec.execute(() -> {
            resultB.set(mockService.doAction2("Y"));
            latch.countDown();
        });

        // 等待异步任务执行完成
        try {
            latch.await();
            // 打印任务执行结果
            log.info("{} -> {}", System.currentTimeMillis(), resultA);
            log.info("{} -> {}", System.currentTimeMillis(), resultB);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        // end: 1539572829124, elapse: 247
        log.info("end: {}, elapse: {}", end, end - begin);

    }

}
