package com.mufusion.adapter.service;


import com.mufusion.adapter.server.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ClientRepository {

    private Map<String, Client> clients = new HashMap<>();

    public void addClient(Client client) {
        clients.put(client.getId(), client);
    }

    public void sendMessage(String clientId, byte[] packet) {
        clients.get(clientId).sendMessage(packet);
    }

}
