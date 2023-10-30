package com.mufusion.adapter.service;

public interface GameServerRepository {

    public void sendInboundMessage(Event event);
}
