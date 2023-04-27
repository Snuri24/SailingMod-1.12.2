package com.life.sailing.network;

import java.util.UUID;

public class ClientPacketFastSailing extends Packet {
	
    public ClientPacketFastSailing(UUID uuid) {
        super(PacketType.CLIENT_FAST_SAILING);
        builder.append(uuid.toString());
    }
    
}
