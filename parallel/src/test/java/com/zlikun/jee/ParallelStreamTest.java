package com.zlikun.jee;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.stream.Stream;

/**
 * @author zlikun
 * @date 2018-10-15 11:42
 */
@Slf4j
public class ParallelStreamTest {


    private MockService mockService = new MockService();

    @Test
    public void test() {

        long begin = System.currentTimeMillis();
        log.info("start: {}", begin);

        Stream.of(
                (Callable<String>) () -> mockService.doAction1("X"),
                () -> mockService.doAction2("Y")
        ).parallel()
                .forEach(callable -> {
                    try {
                        String result = callable.call();
                        log.info("{} -> {}", System.currentTimeMillis(), result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        long end = System.currentTimeMillis();
        // end: 1539581124001, elapse: 226
        log.info("end: {}, elapse: {}", end, end - begin);

    }

}
