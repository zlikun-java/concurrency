package com.zlikun.jee;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.Phaser;

/**
 * https://cloud.tencent.com/developer/article/1350849
 *
 * @author zlikun
 * @date 2018-10-15 13:27
 */
@Slf4j
public class PhaserTest {

    /**
     * phase 阶段，从0开始计数，每当进入下一个阶段，该值递增1
     * parties 缔约数量（线程数），可以在构造方法中指定，也可以使用 #register() 动态添加
     *
     * @throws InterruptedException
     */
    @Test
    public void test0() throws InterruptedException {

        int parties = 2;
        Phaser phaser = new Phaser(parties);

        // RegisteredParties = 2, Phase = 0, ArrivedParties = 0, UnarrivedParties = 2
        log.info("RegisteredParties = {}, Phase = {}, ArrivedParties = {}, UnarrivedParties = {}",
                phaser.getRegisteredParties(),
                phaser.getPhase(),
                phaser.getArrivedParties(),
                phaser.getUnarrivedParties());

        Thread[] threads = new Thread[parties];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread("task-" + i) {
                @Override
                public void run() {
                    sleepMillis(new Random().nextInt() & 0b01111111L);
                    log.info("到达并等待阶段一");
                    phaser.arriveAndAwaitAdvance();

                    sleepMillis(new Random().nextInt() & 0b01111111L);
                    log.info("到达并等待阶段二");
                    phaser.arriveAndAwaitAdvance();
                }
            };
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        // RegisteredParties = 2, Phase = 2, ArrivedParties = 0, UnarrivedParties = 2
        log.info("RegisteredParties = {}, Phase = {}, ArrivedParties = {}, UnarrivedParties = {}",
                phaser.getRegisteredParties(),
                phaser.getPhase(),
                phaser.getArrivedParties(),
                phaser.getUnarrivedParties());

    }

    @Test
    public void test2() throws InterruptedException {

        Phaser phaser = new Phaser();

        // RegisteredParties = 0, Phase = 0, ArrivedParties = 0, UnarrivedParties = 0
        log.info("RegisteredParties = {}, Phase = {}, ArrivedParties = {}, UnarrivedParties = {}",
                phaser.getRegisteredParties(),
                phaser.getPhase(),
                phaser.getArrivedParties(),
                phaser.getUnarrivedParties());

        int len = 3;
        Thread[] threads = new Thread[len];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread("task-" + i) {
                @Override
                public void run() {
                    log.info("开始执行阶段一");
                    sleepMillis(new Random().nextInt() & 0b01111111L);
                    log.info("到达并等待阶段一");
                    phaser.arriveAndAwaitAdvance();
                    log.info("阶段一全部执行完成");
                }
            };
            threads[i].start();
            phaser.register();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        // RegisteredParties = 3, Phase = 1, ArrivedParties = 0, UnarrivedParties = 3
        log.info("RegisteredParties = {}, Phase = {}, ArrivedParties = {}, UnarrivedParties = {}",
                phaser.getRegisteredParties(),
                phaser.getPhase(),
                phaser.getArrivedParties(),
                phaser.getUnarrivedParties());

    }

    @Test
    public void test3() throws InterruptedException {

        int parties = 3;
        Phaser phaser = new Phaser(parties);

        Thread[] threads = new Thread[parties];
        String[] names = {"张三", "李四", "王五"};
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new MockTask(phaser, names[i]), "task-" + i);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        // RegisteredParties = 3, Phase = 6, ArrivedParties = 0, UnarrivedParties = 3
        log.info("RegisteredParties = {}, Phase = {}, ArrivedParties = {}, UnarrivedParties = {}",
                phaser.getRegisteredParties(),
                phaser.getPhase(),
                phaser.getArrivedParties(),
                phaser.getUnarrivedParties());

    }

    class MockTask implements Runnable {

        private Phaser phaser;
        private String name;

        public MockTask(Phaser phaser, String name) {
            this.phaser = phaser;
            this.name = name;
        }

        @Override
        public void run() {
            log.info("{}到丽江", this.name);
            this.phaser.arriveAndAwaitAdvance();
            log.info("{}到玉龙雪山", this.name);
            this.phaser.arriveAndAwaitAdvance();
            log.info("{}下山了", this.name);
            this.phaser.arriveAndAwaitAdvance();
            this.phaser.arriveAndAwaitAdvance();
            this.phaser.arriveAndAwaitAdvance();
            this.phaser.arriveAndAwaitAdvance();
        }
    }

    private void sleepMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
