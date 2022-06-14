package com.mufusion.adapter.packets;

public class ShowLoginAction {

    public byte[] showLogin() {
        GameServerEnteredProtocol gameServerEntered = new GameServerEnteredProtocol();
        return gameServerEntered.toArray();
    }
}
