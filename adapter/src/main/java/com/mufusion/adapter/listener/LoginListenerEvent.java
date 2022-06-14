package com.mufusion.adapter.listener;

import com.mufusion.adapter.packets.AcceptedClient;
import com.mufusion.adapter.packets.GameServerEnteredProtocol;
import com.mufusion.adapter.server.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@AllArgsConstructor
@Slf4j
public class LoginListenerEvent {

    private final ClientService clientService;

    @Bean
    public Consumer<AcceptedClient> onAcceptClient() {
        return (acceptedClient) -> {
            log.info("Client {} accepted", acceptedClient.getId());
            clientService.sendMessage(acceptedClient.getId(), new GameServerEnteredProtocol().toArray());
        };
    }

}
