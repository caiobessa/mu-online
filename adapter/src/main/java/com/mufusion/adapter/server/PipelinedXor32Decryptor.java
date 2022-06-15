package com.mufusion.adapter.server;// <copyright file="PipelinedXor32Decryptor.cs" company="MUnique">

import java.nio.channels.SocketChannel;

public class PipelinedXor32Decryptor extends PacketPipeReaderBase {

    private byte[] _xor32Key;

    /// <summary>
    /// Initializes a new instance of the <see cref="PipelinedXor32Decryptor"/> class.
    /// </summary>
    /// <param name="source">The source.</param>
    public PipelinedXor32Decryptor() {
        this._xor32Key = DefaultKeys.Xor32Key;
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="PipelinedXor32Decryptor"/> class.
    /// </summary>
    /// <param name="source">The source.</param>
    /// <param name="xor32Key">The xor32 key.</param>
    public PipelinedXor32Decryptor(SocketChannel socket, byte[] xor32Key) {
        if (xor32Key.length != 32) {
            throw new RuntimeException("Xor32key must have a size of 32 bytes, but is {xor32Key.Length} bytes long.");
        }
        this._xor32Key = xor32Key;
    }


    /// <summary>
    /// Reads the mu online packet.
    /// Decrypts the packet and writes it into our pipe.
    /// </summary>
    /// <param name="packet">The mu online packet.</param>
    /// <returns>The async task.</returns>
    protected void readPacket(byte[] packet) {
        this.DecryptAndWrite(packet);
    }

    @Override
    protected void OnComplete(Exception exception) {

    }

    private void DecryptAndWrite(byte[] packet) {
        // The next line is getting a span from the writer which is at least as big as the packet.
        // As I found out, it's initially about 2 kb in size and gets smaller within further
        // usage. If the previous span was used up, a new piece of memory is getting provided for us.
        // we just want to work on a span with the exact size of the packet.
        var target = new byte[packet.length];

        var headerSize = ArrayExtensions.GetPacketHeaderSize(target);
        for (var i = target.length - 1; i > headerSize; i--) {
            target[i] = (byte) (target[i] ^ target[i - 1] ^ this._xor32Key[i % 32]);
        }
    }
}