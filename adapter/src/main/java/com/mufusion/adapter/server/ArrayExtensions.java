package com.mufusion.adapter.server;// <copyright file="ArrayExtensions.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>


/// <summary>
/// Extensions for arrays.
/// </summary>
public class ArrayExtensions {
    /// <summary>
    /// Extracts a string of an byte array with the specified encoding.
    /// </summary>
    /// <param name="array">The byte array.</param>
    /// <param name="startIndex">The start index.</param>
    /// <param name="maximumBytes">The maximum length.</param>
    /// <param name="encoding">The encoding.</param>
    /// <returns>The resulting string.</returns>
//    public static string ExtractString(byte[] array, int startIndex, int maximumBytes, Encodin encoding)
//    {
//        int count = array.Skip(startIndex).Take(maximumBytes).TakeWhile(b => b != 0).Count();
//        return encoding.GetString(array, startIndex, count);
//    }

    /// <summary>
    /// Converts the byte array into a readable HEX string.
    /// </summary>
    /// <param name="bytes">The bytes.</param>
    /// <returns>The HEX string.</returns>
//    public static string AsString(this byte[] bytes)
//    {
//        return BitConverter.ToString(bytes).Replace('-', ' ');
//    }

    /// <summary>
    /// Converts bytes of an array to an 32bit unsigned Integer, big endian.
    /// </summary>
    /// <param name="array">Byte array.</param>
    /// <param name="startIndex">Starting index. The array needs 3 more elements.</param>
    /// <returns>An unsigned integer.</returns>
//    public static uint MakeDwordBigEndian(byte[] array, int startIndex)
//    {
//        var source = array.Slice(startIndex, 4);
//        return unchecked(((uint)source[3] << 24) | (uint)(source[2] << 16) | (uint)(source[1] << 8) | source[0]);
//    }

    /// <summary>
    /// Converts bytes of an array to an 32bit unsigned Integer, big endian.
    /// </summary>
    /// <param name="array">Byte array.</param>
    /// <param name="startIndex">Starting index. The array needs 3 more elements.</param>
    /// <returns>An unsigned integer.</returns>
//    public static uint MakeDwordBigEndian(this byte[] array, int startIndex) =>
//        MakeDwordBigEndian(array.AsSpan(), startIndex);

    /// <summary>
    /// Converts bytes of an array to an 32bit unsigned Integer, small endian.
    /// </summary>
    /// <param name="array">Byte array.</param>
    /// <param name="startIndex">Starting index. The array needs 3 more elements.</param>
    /// <returns>An unsigned integer.</returns>
//    public static uint MakeDwordSmallEndian(byte[] array, int startIndex)
//    {
//        var source = array.Slice(startIndex, 4);
//        return unchecked(((uint)source[0] << 24) | (uint)(source[1] << 16) | (uint)(source[2] << 8) | source[3]);
//    }

    /// <summary>
    /// Converts bytes of an array to an unsigned short, small endian.
    /// </summary>
    /// <param name="array">The array.</param>
    /// <param name="startIndex">The start index.</param>
    /// <returns>An unsigned short.</returns>
//    public static ushort MakeWordSmallEndian(byte[] array, int startIndex)
//    {
//        return (ushort)((array[startIndex] << 8) | array[startIndex + 1]);
//    }

    /// <summary>
    /// Converts bytes of an array to an unsigned short, big endian.
    /// </summary>
    /// <param name="array">The array.</param>
    /// <param name="startIndex">The start index.</param>
    /// <returns>An unsigned short.</returns>
//    public static ushort MakeWordBigEndian(byte[] array, int startIndex)
//    {
//        return (ushort)((array[startIndex + 1] << 8) | array[startIndex]);
//    }

    /// <summary>
    /// Converts bytes of an array to an unsigned short, big endian.
    /// If the array is not long enough, it returns 0.
    /// </summary>
    /// <param name="array">The array.</param>
    /// <param name="startIndex">The start index.</param>
    /// <returns>An unsigned short.</returns>
//    public static ushort TryMakeWordBigEndian(byte[] array, int startIndex)
//    {
//        return array.Length > startIndex + 1 ? array.MakeWordBigEndian(startIndex) : default;
//    }

    /// <summary>
    /// Gets the size of the packet header.
    /// </summary>
    /// <param name="packet">The packet.</param>
    /// <returns>The size of the header.</returns>

    /// <summary>
    /// Gets the size of the packet header.
    /// </summary>
    /// <param name="packet">The packet.</param>
    /// <returns>The size of the header.</returns>
    public static int getPacketHeaderSize(byte[] packet) {
        return getPacketHeaderSize(packet[0]);
    }

    /// <summary>
    /// Gets the size of the packet header.
    /// </summary>
    /// <param name="packetPrefix">The packet prefix.</param>
    /// <returns>The size of the header.</returns>
    public static int getPacketHeaderSize(byte packetPrefix) {
        switch (packetPrefix) {
            case (byte) 0xC1:
            case (byte) 0xC3:
                return 2;
            case (byte) 0xC2:
            case (byte) 0xC4:
                return 3;
            default:
                return 0;
        }
    }

    /// <summary>
    /// Gets the type of the packet. This only works when the packet type is not encrypted.
    /// </summary>
    /// <param name="packet">The packet.</param>
    /// <returns>The type of the packet.</returns>
    public static byte GetPacketType(byte[] packet) {
        return packet[getPacketHeaderSize(packet)];
    }

    /// <summary>
    /// Gets the sub type of the packet. This only works when the packet type is not encrypted.
    /// </summary>
    /// <param name="packet">The packet.</param>
    /// <returns>The sub type of the packet.</returns>
    public static byte GetPacketSubType(byte[] packet) {
        if (packet.length > getPacketHeaderSize(packet)) {
            return packet[getPacketHeaderSize(packet) + 1];
        }
        return 0;
    }

    /// <summary>
    /// Gets the size of a packet from its header.
    /// C1 and C3 packets have a maximum length of 255, and the length defined in the second byte.
    /// C2 and C4 packets have a maximum length of 65535, and the length defined in the second and third byte.
    /// </summary>
    /// <param name="packet">The packet.</param>
    /// <returns>The size of a packet.</returns>
    public static int getPacketSize(byte[] packet) {
        switch (packet[0]) {
            case (byte) 0xC1:
            case (byte) 0xC3:
                return packet[1];
            case (byte) 0xC2:
            case (byte) 0xC4:
                return packet[1] << 8 | packet[2];
            default:
                return 0;
        }
    }


    /// <summary>
    /// Sets the size of the byte array as packet length in the corresponding indexes of the byte array.
    /// </summary>
    /// <param name="packet">The packet.</param>
    public static void SetPacketSize(byte[] packet) {
        var size = packet.length;
        switch (packet[0]) {
            case (byte) 0xC1:
            case (byte) 0xC3:
                packet[1] = (byte) size;
                break;
            case (byte) 0xC2:
            case (byte) 0xC4:
                packet[1] = (byte) ((size & 0xFF00) >> 8);
                packet[2] = (byte) (size & 0x00FF);
                break;
            default:
                throw new IllegalArgumentException("Unknown packet type {packet[0]:X}");
        }
    }
}