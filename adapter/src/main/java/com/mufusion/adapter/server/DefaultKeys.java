package com.mufusion.adapter.server;
/// <summary>
/// The default xor keys for encryption and decryption.
/// </summary>
public class DefaultKeys {
    /// <summary>
    /// Gets the default 3 byte long XOR key.
    /// </summary>
    /// <value>
    /// The default 3 byte long XOR key.
    /// </value>
    public static byte[] Xor3Keys = new byte[]{(byte) 0xFC, (byte) (byte) 0xCF, (byte) 0xAB};

    /// <summary>
    /// Gets the default 32 byte long XOR key.
    /// </summary>
    /// <value>
    /// The default 32 byte long XOR key.
    /// </value>
    public static byte[] Xor32Key = new byte[]{
            (byte) 0xAB, 0x11, (byte) 0xCD, (byte) 0xFE, 0x18, 0x23, (byte) 0xC5, (byte) 0xA3,
            (byte) 0xCA, 0x33, (byte) 0xC1, (byte) 0xCC, 0x66, 0x67, 0x21, (byte) 0xF3,
            0x32, 0x12, 0x15, 0x35, 0x29, (byte) 0xFF, (byte) 0xFE, 0x1D,
            0x44, (byte) 0xEF, (byte) 0xCD, 0x41, 0x26, 0x3C, 0x4E, 0x4D,
    };
}