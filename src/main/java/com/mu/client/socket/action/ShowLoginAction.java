package com.mu.client.socket.action;

public class ShowLoginAction {


    public byte[] showLogin(){
        byte[] message = new byte[12];
        message[0] = (byte) 0xC1;
        message[1] = (byte) 12;
        message[2] = (byte) 0xF1;
        message[3] = (byte) 0x00;
        message[4] = (byte) 1;
        message[5] = (byte) 2;
        message[6] = (byte) 0;
        message[7] = (byte) 49;
        message[8] = (byte) 48;
        message[9] = (byte) 52;
        message[10] = (byte) 48;
        message[11] = (byte) 52;
        return message;
    }
}
