package com.zlikun.java;

/**
 * 安全的示例
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-01-05 11:49
 */
public class SafeListener {

    private final EventListener listener;

    private SafeListener() {
        listener = new EventListener() {
            @Override
            public void onEvent(Event e) {
                System.out.println(SafeListener.this);
                System.out.println(e);  // Do something ...
            }
        };
    }

    public static SafeListener newInstance(EventSource source) {
        SafeListener safe = new SafeListener();
        source.registerListener(safe.listener);
        return safe;
    }

}
