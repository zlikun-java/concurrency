package com.zlikun.jee;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;

/**
 * 模拟一些服务(使用HTTP客户端模拟IO操作)，测试其并行执行效果
 *
 * @author zlikun
 * @date 2018-10-15 10:33
 */
@Slf4j
public class MockService {

    private OkHttpClient client = new OkHttpClient.Builder().build();

    /**
     * 动作一
     *
     * @param param
     * @return
     */
    public String doAction1(String param) {
        log.info("{}--doAction1--{}", System.currentTimeMillis(), param);
        // 使用一个HTTP操作来模拟一个IO操作（这里实际是同步的）
        try {
            client.newCall(new Request.Builder().url("https://zlikun.com").build()).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Action-1";
    }

    /**
     * 动作二
     *
     * @param param
     */
    public String doAction2(String param) {
        log.info("{}--doAction2--{}", System.currentTimeMillis(), param);
        // 使用一个HTTP操作来模拟一个IO操作（这里实际是同步的）
        try {
            client.newCall(new Request.Builder().url("https://zlikun.com").build()).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Action-2";
    }

}
