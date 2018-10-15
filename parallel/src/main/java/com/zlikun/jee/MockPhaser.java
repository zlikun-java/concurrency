package com.zlikun.jee;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Phaser;

/**
 * 分阶段执行控制
 *
 * @author zlikun
 * @date 2018-10-15 13:28
 */
@Slf4j
public class MockPhaser extends Phaser {

    public MockPhaser() {
        super();
    }

    public MockPhaser(int parties) {
        super(parties);
    }

    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        log.info(">>>{}, phase = {}, registeredParties = {}", System.currentTimeMillis(), phase, registeredParties);
        switch (phase) {
            case 0:
                return action0();
            case 1:
                return action1();
            case 2:
                return action2();
            default:
                log.info("<<<{}, phase = {}, registeredParties = {}", System.currentTimeMillis(), phase, registeredParties);
                return true;
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean action0() {
        log.info("Action0--{}", System.currentTimeMillis());
        sleep(new Random().nextInt() & 0b01111111);
        return false;
    }

    private boolean action1() {
        log.info("Action1--{}", System.currentTimeMillis());
        sleep(new Random().nextInt() & 0b01111111);
        return false;
    }

    private boolean action2() {
        log.info("Action2--{}", System.currentTimeMillis());
        sleep(new Random().nextInt() & 0b01111111);
        return false;
    }

}
