package com.caio.mu.packets.util;

public class BooleanUtils {

    public Byte transformToByte(Boolean value){
        return value ? (byte)1 : (byte) 0;
    }
}
