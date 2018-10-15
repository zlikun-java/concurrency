package com.zlikun.jee;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 测试Java随机生成器类
 *
 * @author zlikun
 * @date 2018-10-15 10:08
 */
public class SecureRandomTest {

    @Test
    public void test() throws NoSuchAlgorithmException {

        // 在需要频繁生成随机数，或者安全要求较高的时候，不要使用Random，因为其生成的值其实是可以预测的
        // 但其与Random同样的是，如果指定了种子，种子确定后，生成算法相同，那么生成的随机数则是固定的
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();

        // -224635325
        System.out.println(secureRandom.nextInt());
        // -748973743
        System.out.println(secureRandom.nextInt());
        // 45
        System.out.println(secureRandom.nextInt(128));

        // 实测发现即使设置了种子，随机数也是变化的（JDK1.8）
        secureRandom = new SecureRandom("SECURE_RANDOM".getBytes());

        // -713223528
        System.out.println(secureRandom.nextInt());
        // -469260609
        System.out.println(secureRandom.nextInt());
        // 64
        System.out.println(secureRandom.nextInt(128));

        // 指定算法
        secureRandom = SecureRandom.getInstance("SHA1PRNG");

        // 1028348061
        System.out.println(secureRandom.nextInt());
        // 607587569
        System.out.println(secureRandom.nextInt());
        // 13
        System.out.println(secureRandom.nextInt(128));
    }

}
