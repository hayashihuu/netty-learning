package com.syun.nettydemo06;

import com.syun.nettydemo06.client.TimeClient;
import com.syun.nettydemo06.server.TimeServer;
import org.testng.annotations.Test;

/*
 * @description:
 * @program: netty-demo04
 * @author: syun
 * @create: 2019-05-04 16:35
 */
public class TestDemo {

    @Test
    public void testServer() throws Exception {
        new TimeServer().bind(8080);
    }

    @Test
    public void testClient() throws Exception {
        new TimeClient().connect(8080, "127.0.0.1");
    }

    @Test
    public void testClient1() throws Exception {
        new TimeClient().connect(8080, "127.0.0.1");
    }

    @Test
    public void test() {

    }


}
