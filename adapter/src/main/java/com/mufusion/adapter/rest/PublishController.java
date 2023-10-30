package com.mufusion.adapter.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PublishController {



    @GetMapping(value = "/publish")
    public String publish() {
        // streamBridge.send("producer-out-0", Person.builder().name(UUID.randomUUID().toString()).build());
        return "event published";
    }


}