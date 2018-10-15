package com.zlikun.jee;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author zlikun
 * @date 2018-10-15 11:16
 */
@Slf4j
public class ForkJoinTest {

    private MockService mockService = new MockService();

    @Test
    public void test() {

        ForkJoinPool forkJoinPool = new ForkJoinPool(4);

        long begin = System.currentTimeMillis();
        log.info("start: {}", begin);

        // 并行TaskA
        RecursiveTask<String> taskA = new RecursiveTask<String>() {
            @Override
            protected String compute() {
                return mockService.doAction1("X");
            }
        };
        // 并行TaskB
        RecursiveTask<String> taskB = new RecursiveTask<String>() {
            @Override
            protected String compute() {
                return mockService.doAction2("Y");
            }
        };

        List<String> results = forkJoinPool.invoke(new ActionTask(new RecursiveTask[]{taskA, taskB}));
        // 打印任务执行结果
        results.forEach(result -> log.info("{} -> {}", System.currentTimeMillis(), result));

        long end = System.currentTimeMillis();
        // end: 1539572829124, elapse: 247
        log.info("end: {}, elapse: {}", end, end - begin);

    }

    class ActionTask extends RecursiveTask<List<String>> {

        private RecursiveTask<String>[] tasks;

        public ActionTask(RecursiveTask<String>[] tasks) {
            this.tasks = tasks;
        }

        @Override
        protected List<String> compute() {
            if (tasks == null || tasks.length == 0) {
                return null;
            }

            for (RecursiveTask<String> task : tasks) {
                task.fork();
            }

            List<String> results = new ArrayList<>();
            for (RecursiveTask<String> task : tasks) {
                results.add(task.join());
            }
            return results;
        }
    }

}
