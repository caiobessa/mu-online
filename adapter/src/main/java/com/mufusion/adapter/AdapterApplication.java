package com.mufusion.adapter;

import com.mufusion.adapter.service.GameServerSockets;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
@AllArgsConstructor
public class AdapterApplication {

    private final GameServerSockets gameServerSockets;

    @PostConstruct
    public void startSockets() throws IOException {
        gameServerSockets.start();
    }

    public static void main(String[] args) {
        SpringApplication.run(AdapterApplication.class, args);
    }

}
