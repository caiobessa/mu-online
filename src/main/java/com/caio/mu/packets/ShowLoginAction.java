package com.caio.mu.packets;

public class ShowLoginAction {

    public byte[] showLogin() {
        GameServerEntered gameServerEntered = new GameServerEntered();
        return gameServerEntered.toArray();
    }
}
