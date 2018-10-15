package com.zlikun.jee;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 测试串行执行
 *
 * @author zlikun
 * @date 2018-10-15 11:03
 */
@Slf4j
public class MockServiceTest {

    private MockService mockService = new MockService();

    @Test
    public void test() {

        long begin = System.currentTimeMillis();
        log.info("start: {}", begin);

        String resultA = mockService.doAction1("X");
        log.info("{} -> {}", System.currentTimeMillis(), resultA);

        String resultB = mockService.doAction2("Y");
        log.info("{} -> {}", System.currentTimeMillis(), resultB);

        long end = System.currentTimeMillis();
        // end: 1539572794965, elapse: 347
        log.info("end: {}, elapse: {}", end, end - begin);
    }

}
