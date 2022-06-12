package com.caio.mu.packets;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GameServerEntered extends C1HeaderWithSubCode {

    public static final Byte HEADER_TYPE = (byte) 0xC1;
    public static final Byte CODE = (byte) 0xF1;
    public static final Byte LENGTH = (byte) 12;
    public static final Byte SUB_CODE = (byte) 0x00;

    private Boolean success;
    private Integer clientId;
    private String VersionString;
    private String Version;

    private GameServerEntered(String versionString, String version, Integer clientId) {
        super(HEADER_TYPE, LENGTH, CODE, SUB_CODE);
        success = true;
        setVersionString(versionString);
        setVersion(version);
        setClientId(clientId);
    }

    public List<Byte> getData() {
        List<Byte> convert = convert();
        convert.set(4, (byte) 1);
        return convert;
    }

//    public C1HeaderWithSubCode Header = (byte) new (this._data);
//
//    /// <summary>
//    /// Gets or sets the success.
//    /// </summary>
//    public bool Success
//
//    {
//        get = (byte) this._data[4..].GetBoolean();
//        set =>this._data[4..].SetBoolean(value);
//    }
//
//    /// <summary>
//    /// Gets or sets the player id.
//    /// </summary>
//    public ushort PlayerId
//
//    {
//        get =>ReadUInt16BigEndian(this._data[5..]);
//        set =>WriteUInt16BigEndian(this._data[5..], value);
//    }
//
//    /// <summary>
//    /// Gets or sets the version string.
//    /// </summary>
//    public string VersionString
//
//    {
//        get =>this._data.ExtractString(7, 5, System.Text.Encoding.UTF8);
//        set =>this._data.Slice(7, 5).WriteString(value, System.Text.Encoding.UTF8);
//    }
//
//    /// <summary>
//    /// Gets or sets the version.
//    /// </summary>
//    public Span<byte> Version
//
//    {
//        get =>this._data.Slice(7, 5);
//    }

//    /// <summary>
//    /// Performs an implicit conversion from a Span of bytes to a <see cref="GameServerEntered"/>.
//    /// </summary>
//    /// <param name="packet">The packet as span.</param>
//    /// <returns>The packet as struct.</returns>
//    public static implicit operator GameServerEntered(Span<byte> packet) => new (packet, false);
//
//    /// <summary>
//    /// Performs an implicit conversion from <see cref="GameServerEntered"/> to a Span of bytes.
//    /// </summary>
//    /// <param name="packet">The packet as struct.</param>
//    /// <returns>The packet as byte span.</returns>
//    public static implicit operator Span<byte>(GameServerEntered packet) => packet._data;

}
