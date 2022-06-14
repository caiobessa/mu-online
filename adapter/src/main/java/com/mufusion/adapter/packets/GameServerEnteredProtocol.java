package com.mufusion.adapter.packets;


import com.mufusion.adapter.packets.util.BooleanUtils;
import com.mufusion.adapter.packets.util.MuArraysUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class GameServerEnteredProtocol extends C1HeaderWithSubCode {

    public static final Byte PLAYER_ID = (byte) 0x0200;
    public static final Byte HEADER_TYPE = (byte) 0xC1;
    public static final Byte CODE = (byte) 0xF1;
    public static final Byte LENGTH = (byte) 12;
    public static final Byte SUB_CODE = (byte) 0x00;

    private Boolean success;
    private Integer clientId;
    private String VersionString;
    private String Version;
    private Byte playerId = PLAYER_ID;
    private ClientVersion clientVersion = new ClientVersion();

    public GameServerEnteredProtocol() {
        super(HEADER_TYPE, LENGTH, CODE, SUB_CODE);
        this.success = true;
        this.clientId = getClientId();
    }

    public byte[] toArray() {
        var packageHeader = super.toArray();
        var clientVersionAsArray = clientVersion.toArray();
        var gameServerEnteredAsArray = Arrays.copyOf(packageHeader, getLength());
        gameServerEnteredAsArray[4] = (byte) new BooleanUtils().transformToByte(success);
        gameServerEnteredAsArray[5] = playerId;
        gameServerEnteredAsArray[6] = 0;
        new MuArraysUtils().copy(7,gameServerEnteredAsArray, clientVersionAsArray);
        return gameServerEnteredAsArray;
    }

}
