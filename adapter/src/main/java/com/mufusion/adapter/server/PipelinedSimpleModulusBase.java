package com.mufusion.adapter.server;// <copyright file="PipelinedSimpleModulusBase.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>


import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Getter
public class PipelinedSimpleModulusBase extends PacketPipeReaderBase {
    /// <summary>
    /// The xor key which is used as to 'encrypt' the size of each block.
    /// </summary>
    protected final byte BlockSizeXorKey = 0x3D;

    /// <summary>
    /// The xor key which is used as to 'encrypt' the checksum of each encrypted block.
    /// </summary>
    protected final byte BlockCheckSumXorKey = (byte) 0xF8;

    private final int BitsPerByte = 8;

    private final int bitsPerValue = (BitsPerByte * 2) + 2;
    private int decryptedBlockSize = 8;
    private int encryptedBlockSize = 11;
    private long[] encryptionResult = new long[4];
    private Counter counter = new Counter();



    /// <summary>
    /// Initializes a new instance of the <see cref="PipelinedSimpleModulusBase"/> class.
    /// </summary>
    /// <param name="variant">Variant of the algorithm.</param>
    protected PipelinedSimpleModulusBase(Variant variant) {
        if (variant == Variant.New) {
            // newer versions
            this.decryptedBlockSize = 8;
            this.encryptedBlockSize = 11;
            this.encryptionResult = new long[4];
            this.counter = new Counter();
        } else {
            this.decryptedBlockSize = 32;
            this.encryptedBlockSize = 38;
            this.encryptionResult = new long[16];
        }
    }



    @Override
    protected void readPacket(byte[] packet) {

    }

    @Override
    protected void OnComplete(Exception exception) {

    }

    /// <summary>
    /// The variant of the algorithm.
    /// </summary>
    enum Variant {
        /// <summary>
        /// The newer variant, where the unencrypted block size is 8 bytes, and encrypted is 11 bytes.
        /// Uses a counter.
        /// </summary>
        New,

        /// <summary>
        /// The older variant, where the unencrypted block size is 32 bytes and encrypted is 38 bytes.
        /// Doesn't use a counter.
        /// </summary>
        Old,
    }

    /// <summary>
    /// Gets the decrypted block size in bytes.
    /// </summa

    /// <summary>
    /// Gets the encrypted block size in bytes.
    /// It's bigger than the decrypted size, because it contains the length of the actual data of the block and a checksum.
    /// Basically, you can calculate it by <see cref="DecryptedBlockSize"/> / 8 bits * 10 bits + 2 bytes.
    /// </summary>


    /// <summary>
    /// Gets the counter.
    /// </summary>


    /// <summary>
    /// Gets the ring buffer.
    /// </summary>

    /// <summary>
    /// Gets the header buffer of the currently read packet.
    /// </summary>
    protected byte[] getHeaderBuffer() {
        return new byte[3];
    }


    /// <summary>
    /// Resets this instance.
    /// </summary>
    public void Reset() {
        this.counter.Reset();
    }

    public byte getBlockSizeXorKey() {
        return BlockSizeXorKey;
    }

    /// <summary>
    /// Gets the byte offset in the encrypted block buffer based on the index of <see cref="EncryptionResult"/>.
    /// </summary>
    /// <param name="resultIndex">Index of the <see cref="EncryptionResult"/>.</param>
    /// <returns>The byte offset in the encrypted block buffer based on the index of <see cref="EncryptionResult"/>.</returns>
    protected  int getByteOffset(int resultIndex) {
        return getByteIndex(resultIndex) / BitsPerByte;
    }

    public int getByteIndex(int resultIndex) {
        return resultIndex / BitsPerByte;
    }

/// <summary>
/// Gets the bit offset in the encrypted block buffer based on the index of <see cref="EncryptionResult"/>.
/// </summary>
/// <param name="resultIndex">Index of the <see cref="EncryptionResult"/>.</param>
/// <returns>The bit offset in the encrypted block buffer based on the index of <see cref="EncryptionResult"/>.</returns>

    protected int getBitOffset(int resultIndex) {
        return getBitIndex(resultIndex) % BitsPerByte;
    }


    /// <summary>
/// Gets the bit mask of the first byte (at the index of <see cref="GetByteOffset(int)"/>) in the encrypted block buffer based on the index of <see cref="EncryptionResult"/>.
/// </summary>
/// <param name="resultIndex">Index of the <see cref="EncryptionResult"/>.</param>
/// <returns>The the bit mask of the first byte (at the index of <see cref="GetByteOffset(int)"/>) in the encrypted block buffer based on the index of <see cref="EncryptionResult"/>.</returns>
    protected int getFirstBitMask(int resultIndex) {
        return 0xFF >> getBitOffset(resultIndex);
    }


    /// <summary>
/// Gets the bit mask of the last byte (at the index of <see cref="GetByteOffset(int)"/>) in the encrypted block buffer based on the index of <see cref="EncryptionResult"/>,
/// just for the last remainder of the encryption result value. The remainder is basically the first 2 bits, e.g. when the value is <c>0x2FFFF</c>, the remainder is the 2 in front.
/// </summary>
/// <param name="resultIndex">Index of the <see cref="EncryptionResult"/>.</param>
/// <returns>The the bit mask of the first byte (at the index of <see cref="GetByteOffset(int)"/>) in the encrypted block buffer based on the index of <see cref="EncryptionResult"/>.</returns>
    protected int getRemainderBitMask(int resultIndex) {
        return (0xFF << (6 - getBitIndex(resultIndex)) & 0xFF) - ((0xFF << (8 - getBitIndex(resultIndex))) & 0xFF);
    }

    /// <summary>
/// Copies the data of the packet into the target writer, without flushing it yet.
/// </summary>
/// <param name="target">The target writer.</param>
/// <param name="packet">The packet.</param>
    protected void CopyDataIntoWriter(SocketChannel target, Byte[] packet) {
//        ByteBuffer byteBuffer = ByteBuffer.allocate(2000);
//            var packetSize = getHeaderBuffer().length;
//            try {
//                var data = target.read(byteBuffer)(0, packetSize);
//            }catch (IOException e){
//
//         }
//
//
//            packet.CopyTo(data);
//            target.Advance(packetSize);
    }

    /// <summary>
/// Gets the size of the content.
/// </summary>
/// <param name="packet">The packet.</param>
/// <param name="decrypted">if set to <c>true</c> it is decrypted. Encrypted packets additionally contain a counter.</param>
/// <returns>The size of the actual content.</returns>
    protected int getContentSize(byte[] packet, boolean decrypted) {
        var contentSize = packet.length - packet[3];

        if (this.counter != null && decrypted) {
            contentSize++;
        }
        return contentSize;
    }

    protected int getContentSize(ByteBuffer packet, boolean decrypted) {
        var asArray = packet.array();
        var contentSize = ArrayExtensions.getPacketSize(asArray) - ArrayExtensions.getPacketHeaderSize(asArray);

        if (this.counter != null && decrypted) {
            contentSize++;
        }
        return contentSize;
    }

    private int getBitIndex(int resultIndex) {
        return resultIndex * bitsPerValue;
    }
}