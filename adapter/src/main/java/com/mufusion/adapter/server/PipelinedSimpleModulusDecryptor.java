package com.mufusion.adapter.server;


import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/// <summary>
/// A decryptor which decrypts 0xC3 and 0xC4-packets with the "simple modulus" algorithm.
/// </summary>
// IPipelinedDecryptor
@Slf4j
public class PipelinedSimpleModulusDecryptor extends PipelinedSimpleModulusBase {

    private ArrayExtensions arrayExtensions = new ArrayExtensions();
    /// <summary>
    /// The default server side decryption key. The corresponding encryption key is <see cref="PipelinedSimpleModulusEncryptor.DefaultClientKey"/>.
    /// </summary>
    public static SimpleModulusKeys DefaultServerKey = SimpleModulusKeys.CreateDecryptionKeys(new long[]{128079, 164742, 70235, 106898, 31544, 2047, 57011, 10183, 48413, 46165, 15171, 37433});

    /// <summary>
    /// The default client side decryption key. The corresponding encryption key is <see cref="PipelinedSimpleModulusEncryptor.DefaultServerKey"/>.
    /// </summary>
    public static SimpleModulusKeys DefaultClientKey = SimpleModulusKeys.CreateDecryptionKeys(new long[]{73326, 109989, 98843, 171058, 18035, 30340, 24701, 11141, 62004, 64409, 35374, 64599});

    private SimpleModulusKeys _decryptionKeys;
    private byte[] _inputBuffer;

    /// <summary>
    /// Initializes a new instance of the <see cref="PipelinedSimpleModulusDecryptor"/> class with standard keys.
    /// </summary>
    /// <param name="source">The source.</param>
    public PipelinedSimpleModulusDecryptor() {
        super(Variant.New);
        this._decryptionKeys = DefaultClientKey;
        this._inputBuffer = new byte[getEncryptedBlockSize()];
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="PipelinedSimpleModulusDecryptor"/> class.
    /// </summary>
    /// <param name="source">The source.</param>
//    /// <param name="decryptionKeys">The decryption keys.</param>
//    public PipelinedSimpleModulusDecryptor(SocketChannel source, SimpleModulusKeys decryptionKeys) {
//        super(Variant.New);
//        this.Source = source;
//        this._decryptionKeys = decryptionKeys;
//        this._inputBuffer = new byte[getEncryptedBlockSize()];
//        // read the package here this.readPacket(by)
//    }


    /// <summary>
    /// Gets or sets a value indicating whether this decryptor instance accepts wrong block checksum, or throws an exception in this case.
    /// </summary>
    public Boolean acceptWrongBlockChecksuml;


    /// <summary>
    /// Reads the mu online packet.
    /// Decrypts the packet and writes it into our pipe.
    /// </summary>
    /// <param name="packet">The mu online packet.</param>
    /// <returns>The async task.</returns>
    public int readRawPack(ByteBuffer rawMuPacket) {
        // The next line is getting a span from the writer which is at least as big as the packet.
        // As I found out, it's initially about 2 kb in size and gets smaller within further
        // usage. If the previous span was used up, a new piece of memory is getting provided for us.

        if (rawMuPacket.get(0) < (byte) 0xC3) {

            log.info("new clint calling ");

            // just call the event handler

//            // we just have to write-through
//            this.CopyDataIntoWriter(this.Pipe.Writer, packet);
//            await this.Pipe.Writer.FlushAsync().ConfigureAwait(false);
        } else {
            var contentSize = getContentSize(rawMuPacket, false);
            if ((contentSize % this.getEncryptedBlockSize()) != 0) {
                throw new IllegalArgumentException("The packet has an unexpected content size. It must be a multiple of {this.EncryptedBlockSize}");
            }

            this.DecryptAndWrite(rawMuPacket);
        }

        return rawMuPacket.remaining();
    }

    private void DecryptAndWrite(ByteBuffer rawMuPacket) {
        var maximumDecryptedSize = getMaximumDecryptedSize(rawMuPacket);
        var headerSize = getPacketHeaderSize(rawMuPacket);
        var counterSize = getCounter() == null ? 0 : 1;

        var decrypted = ByteBuffer.allocate(maximumDecryptedSize);

        var decryptedContentSize = this.DecryptPacketContent(rawMuPacket.slice(headerSize, rawMuPacket.remaining() - headerSize),
                decrypted.slice(headerSize - counterSize, maximumDecryptedSize - headerSize)); // if we have a counter, we trick a bit by passing in a bigger span
//        decrypted.
//        decrypted = decrypted.Slice(0, decryptedContentSize + headerSize - counterSize);
//        decrypted.SetPacketSize();

        //    this.Pipe.Writer.Advance(decrypted.Length);
    }

    private int DecryptPacketContent(ByteBuffer input, ByteBuffer output) {
        int sizeCounter = 0;
        var rest = input;
        do
        {
            rest.slice(0, getEncryptedBlockSize() ).get(this._inputBuffer);
            var outputBlock = output.slice(sizeCounter, this.getDecryptedBlockSize());
            var blockSize = this.DecryptBlock(outputBlock);
            if (getCounter() != null && sizeCounter == 0 && outputBlock.get(0) != getCounter().get_counter()) {
                log.info("Error on DecryptPacketContent {}, {}", outputBlock.get(0), (byte)  getCounter().get_counter());
                throw new IllegalArgumentException("Erro on decryption");
            }

            if (blockSize != -1)
            {
                sizeCounter += blockSize;
            }

            rest = rest.slice(0,getEncryptedBlockSize());
        }
        while (rest.remaining() > 0);

        getCounter().Increase();
        return sizeCounter;
    }

        /// <summary>
        /// Decrypts the block.
        /// </summary>
        /// <param name="outputBuffer">The output buffer array.</param>
        /// <returns>The decrypted length of the block.</returns>
        private int DecryptBlock (ByteBuffer outputBuffer){
            for (int i = 0; i < getEncryptionResult().length; i++) {
                getEncryptionResult()[i] = readInputBuffer(i);
            }
//
            this.decryptContent(outputBuffer);
//
//            return this.DecodeFinal(outputBuffer);
            return 0;
        }

    private void decryptContent(ByteBuffer outputBuffer) {
        var keys = this._decryptionKeys;
        for (int i = getEncryptionResult().length - 1; i > 0; i--) {
            getEncryptionResult()[i - 1] =getEncryptionResult()[i - 1] ^ keys.getXorKey()[i - 1] ^ (getEncryptionResult()[i] & 0xFFFF);
        }

//        var output = MemoryMarshal.Cast < byte,ushort > (outputBuffer);
//        for (int i = 0; i < this.EncryptionResult.Length; i++) {
//            uint result = keys.XorKey[i] ^ ((this.EncryptionResult[i] * keys.DecryptKey[i]) % keys.ModulusKey[i]);
//            if (i > 0) {
//                result ^= this.EncryptionResult[i - 1] & 0xFFFF;
//            }
//
//            output[i] = (ushort) result;
//        }
    }

    private long readInputBuffer(int resultIndex) {
//        var byteOffset = GetByteOffset(resultIndex);
//        var bitOffset = GetBitOffset(resultIndex);
//        var firstMask = GetFirstBitMask(resultIndex);
//        uint result = 0;
//        result += (uint) ((this._inputBuffer[byteOffset++] & firstMask) << (24 + bitOffset));
//        result += (uint) (this._inputBuffer[byteOffset++] << (16 + bitOffset));
//        result += (uint) ((this._inputBuffer[byteOffset] & (0xFF << (8 - bitOffset))) << (8 + bitOffset));
//
//        result = ReverseEndianness(result);
//        var remainderMask = GetRemainderBitMask(resultIndex);
//        var remainder = (byte) (this._inputBuffer[byteOffset] & remainderMask);
//        result += (uint) (remainder << 16) >> (6 - bitOffset);
//
//        return result;
        return 0l;
    }

        /// <summary>
        /// Decodes the last block which contains the checksum and the block size.
        /// </summary>
        /// <param name="outputBuffer">The output buffer array.</param>
        /// <returns>The decrypted length of the block.</returns>
//    private int DecodeFinal(ByteBuffer outputBuffer) {
//        var blockSuffix = this._inputBuffer.AsSpan(this.EncryptedBlockSize - 2, 2);
//        //// blockSuffix[0] -> block size (encrypted)
//        //// blockSuffix[1] -> checksum
//
//        byte blockSize = (byte) (blockSuffix[0] ^ blockSuffix[1] ^ BlockSizeXorKey);
//
//        if (blockSize > this.DecryptedBlockSize) {
//            throw new InvalidBlockSizeException(blockSize, this.DecryptedBlockSize);
//        }
//
//        byte checksum = BlockCheckSumXorKey;
//        for (int i = 0; i < this.DecryptedBlockSize; i++) {
//            checksum ^= outputBuffer[i];
//        }
//
//        if (blockSuffix[1] != checksum) {
//            if (!this.getAcceptWrongBlockChecksum()) {
//                throw new InvalidBlockChecksumException(blockSuffix[1], checksum);
//            }
//
//            //  Debug.WriteLine($"Block checksum invalid. Expected: {checksum}. Actual: {blockSuffix[1]}.");
//        }
//
//        return blockSize;
//    }


        private int getMaximumDecryptedSize (ByteBuffer byteBuffer){
            int packetHeaderSize = getPacketHeaderSize(byteBuffer);
            int contentSize = byteBuffer.remaining() - packetHeaderSize;
            if (this.getCounter() != null) {
                // as we don't forward the counter value, we can subtract one
                contentSize--;
            }

            return (contentSize * getDecryptedBlockSize() / getEncryptedBlockSize()) + packetHeaderSize;
        }

        private int getPacketHeaderSize (ByteBuffer byteBuffer){
            return arrayExtensions.getPacketHeaderSize(byteBuffer.array());
        }
    }