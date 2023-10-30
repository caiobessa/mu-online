package com.mufusion.adapter.listener;

import com.mufusion.adapter.packets.AcceptedClient;
import com.mufusion.adapter.packets.GameServerEnteredProtocol;
import com.mufusion.adapter.server.ClientService;
import com.mufusion.adapter.service.EventType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@AllArgsConstructor
@Slf4j
public class LoginListenerEvent implements EventListener {

    private final ClientService clientService;

    public Consumer<AcceptedClient> onEvent() {
        return (AcceptedClient) -> {
            log.info("Client {} accepted", AcceptedClient.getId());
            clientService.sendMessage(AcceptedClient.getId(), new GameServerEnteredProtocol().toArray());
        };
    }

    public EventType getEventType() {
        return EventType.ACCEPTED_CLIENT;
    }

}
