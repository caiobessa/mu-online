package com.mufusion.adapter.packets;

import com.mufusion.adapter.service.Event;
import com.mufusion.adapter.service.EventType;
import lombok.*;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Setter
@Builder
public class AcceptedClient implements Event {

    private String id;

    @Override
    public EventType getEventType(){
        return EventType.ACCEPTED_CLIENT;
    }
}
