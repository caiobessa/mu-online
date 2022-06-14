package com.mufusion.adapter.controller.rest;

import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PublishController {

    public final StreamBridge streamBridge;


    @GetMapping(value = "/publish")
    public String publish() {
        // streamBridge.send("producer-out-0", Person.builder().name(UUID.randomUUID().toString()).build());
        return "event published";
    }


}