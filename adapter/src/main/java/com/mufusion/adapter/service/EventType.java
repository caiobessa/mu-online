package com.mufusion.adapter.service;

import com.mufusion.adapter.packets.AcceptedClient;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EventType {

    ACCEPTED_CLIENT("ACCEPTED_EVENT", AcceptedClient.class);

    private String name;
    private Class clazz;
}
