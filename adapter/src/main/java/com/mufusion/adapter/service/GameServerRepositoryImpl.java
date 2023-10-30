package com.mufusion.adapter.service;

import com.mufusion.adapter.listener.EventListener;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class GameServerRepositoryImpl implements GameServerRepository {

    private final List<EventListener> listeners;

    @Override
    public void sendInboundMessage(Event event) {
        listeners.forEach(e -> {
           if(e.getEventType().equals(event.getEventType())){
                var onEvent = e.onEvent();
                onEvent.accept(event);
            }
        });
    }
}
