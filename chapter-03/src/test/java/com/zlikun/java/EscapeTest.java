package com.zlikun.java;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-01-05 11:33
 */
public class EscapeTest {

    @Test
    public void test() {

        UnsafeStates us = new UnsafeStates();
        assertArrayEquals(new String [] { "AK", "AL" }, us.getStates());

        // 在外部修改(原则上封装对象的内部属性是不应该被外部修改的)
        us.getStates()[1] = "KILL";

        // 可以看到对象内部的属性被改变了，即对象逸出了(如果是需要被外部使用并修改的称为发布，即：发布是有利还是有害的，个人理解)
        assertArrayEquals(new String [] { "AK", "KILL" }, us.getStates());

    }

    class UnsafeStates {

        private String [] states = new String [] { "AK", "AL" };

        /**
         * 对象被发布，可以被动
         * @return
         */
        public String[] getStates() {
            return states;
        }
    }

}
