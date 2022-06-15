package com.mufusion.adapter.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public abstract class PacketPipeReaderBase {


    private  byte[] _headerBuffer = new byte[3];


    protected SocketChannel Source;


    protected abstract void readPacket(byte[] packet);


    protected abstract void OnComplete(Exception exception);


    private  void ReadBuffer() throws IOException {

        ByteBuffer bufferIn = ByteBuffer.allocate(1200);
        this.Source.read(bufferIn);
        //ReadOnlySequence<byte> buffer = result.Buffer;
        int length = 0;
        byte[] bufferAsArray = bufferIn.array();

        if (bufferIn.array().length > 2) {
                // peek the length of the next packet
                _headerBuffer = bufferIn.slice(0, 3).array();
                length = this._headerBuffer.length;
                if (length == 0)
                {
                    throw new RuntimeException("packet without payload");

//                    // Notify our source, that we don't intend to read anymore.
//                    await this.Source.CompleteAsync(exception).ConfigureAwait(false);
//
//                    this.OnComplete(exception);
//                    throw exception;
                }
            }
        readPacket(bufferAsArray);

//        if (length  > 0 && bufferAsArray.length >= length){
//                var packet = bufferAsArray.Slice(0, length.Value);
                //this.ReadPacket(packet).ConfigureAwait(false);
//
////                buffer = buffer.Slice(buffer.GetPosition(length.Value), buffer.End);
////                length = null;
//            }
//            else
//            {
//                // read more
//                break;
//            }


//            if (length  > 0 && bufferAsArray.length >= length){
//                var packet = bufferAsArray.Slice(0, length.Value);
//                //this.ReadPacket(packet).ConfigureAwait(false);
//
////                buffer = buffer.Slice(buffer.GetPosition(length.Value), buffer.End);
////                length = null;
//            }
//            else
//            {
//                // read more
//                break;
//            }
//        }
//        while (buffer.Length > 2);
//
//        if (result.IsCanceled || result.IsCompleted)
//        {
//            // Not possible to advance any further, e.g. because of a disconnected network connection.
//            this.OnComplete(null);
//        }
//        else
//        {
//            // Tell the PipeReader how much of the buffer we have consumed
//            this.Source.AdvanceTo(buffer.Start);
//        }
//
//        return result.IsCompleted;
    }
}