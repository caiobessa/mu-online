package com.mufusion.adapter.packets;

import com.mufusion.adapter.server.Client;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Setter
@Builder
public class AcceptedClient {

    private String id = UUID.randomUUID().toString();
}
