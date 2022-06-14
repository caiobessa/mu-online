package com.mufusion.adapter.packets;

public class ClientVersion {

    public byte[] toArray() {
        return new byte[]{(byte) 49, (byte) 48, (byte) 52, (byte) 48, (byte) 52};
    }
}
