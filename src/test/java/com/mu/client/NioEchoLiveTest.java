package com.mu.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class NioEchoLiveTest {

    private Process server;
    private EchoClient client;

    @Before
    public void setup() throws IOException, InterruptedException {
        server = EchoServer.start();
        Thread.sleep(1000);
        client = EchoClient.start();

    }

    @Test
    public void givenServerClient_whenServerEchosMessage_thenCorrect()throws Exception {

        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        assertEquals("hello", resp1);
        assertEquals("world", resp2);
    }

    @After
    public void teardown() throws IOException {
        server.destroy();
        client.stop();
    }
}