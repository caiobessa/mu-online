package com.caio.mu.packets;


import com.caio.mu.packets.util.BooleanUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class GameServerEntered extends C1HeaderWithSubCode {

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

    public GameServerEntered() {
        super(HEADER_TYPE, LENGTH, CODE, SUB_CODE);
        success = true;
        setClientId(clientId);
    }

    public byte[] toArray() {
        var clientVersionAsArray = clientVersion.toArray();
        var gameServerEnteredAsArray = Arrays.copyOf(super.toArray(), getLength());
        gameServerEnteredAsArray[4] = (byte) new BooleanUtils().transformToByte(success);
        gameServerEnteredAsArray[5] = playerId;
        gameServerEnteredAsArray[6] = 0;
        gameServerEnteredAsArray[7] = clientVersionAsArray[0];
        gameServerEnteredAsArray[8] = clientVersionAsArray[1];
        gameServerEnteredAsArray[9] = clientVersionAsArray[2];
        gameServerEnteredAsArray[10] = clientVersionAsArray[3];
        gameServerEnteredAsArray[11] = clientVersionAsArray[4];
        return gameServerEnteredAsArray;
    }

}
