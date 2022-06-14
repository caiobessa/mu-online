package com.mufusion.adapter.server;


import com.mufusion.adapter.service.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public void addClient(Client client){
        clientRepository.addClient(client);
    }

    public void sendMessage(String clientId, byte[] message){
        clientRepository.sendMessage(clientId,message);
    }
}
