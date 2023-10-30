package com.mufusion.adapter.listener;

import com.mufusion.adapter.service.EventType;

import java.util.function.Consumer;

public interface EventListener<T> {

    public Consumer<T> onEvent();

    public EventType getEventType();
}
