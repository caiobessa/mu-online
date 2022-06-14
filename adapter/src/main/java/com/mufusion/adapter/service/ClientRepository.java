package com.mufusion.adapter.service;


import com.mufusion.adapter.packets.AcceptedClient;
import com.mufusion.adapter.packets.ShowLoginAction;
import com.mufusion.adapter.server.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Service
@Slf4j
public class ClientRepository {

    private Map<String, Client> clients = new HashMap<>();

    public void addClient(Client client, String id){
        clients.put(id, client);
    }

    @Bean
    public Consumer<AcceptedClient> onAcceptClient() {
        return (acceptedClient) -> {
            log.info("Client {} accepted", acceptedClient.getId());
            Client client = clients.get(acceptedClient.getId());
            client.sendMessage(new ShowLoginAction().showLogin());
        };
    }

}
