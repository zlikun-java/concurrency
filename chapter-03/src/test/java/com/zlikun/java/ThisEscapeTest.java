package com.zlikun.java;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;


/**
 * 对象的逸出测试
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-01-05 09:49
 */
public class ThisEscapeTest {

    @Test
    public void test() {

        new ThisEscape(new EventSource());

    }

    /**
     * 参考一个安全的实现(对象不会逸出)
     * @see SafeListener
     */
    public class ThisEscape {

        public ThisEscape(EventSource source) {
            source.registerListener(new EventListener() {
                @Override
                public void onEvent(Event e) {
                    // 在构造 EventListener 这个内部类实例时，隐含地发布了 ThisEscape 实例本身，见：ThisEscape.this 语句
                    // 无论是否使用了该实例(ThisEscape.this)或者执行了何种操作都不重要，因为误用的风险始终存在
                    // 实际上本例并不会造成实例逸出，因为构造函数未执行完成，发布的是一个未完成的对象
                    // 一个错误的使用场景是，如果在构造函数中使用线程并启动它，则无论对象是否构造完成，都会造成对象逸出
                    System.out.println(ThisEscape.this);
                    System.out.println(e);  // Do something ...
                }
            });
        }
    }

}
