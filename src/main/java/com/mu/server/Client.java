package com.mu.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class Client {

    ByteBuffer bufferIn;
    ByteBuffer bufferOut;

    SelectionKey key;
    SocketChannel socket;
    String ipAddress;

    public Client(String ipAddress, SocketChannel socket, SelectionKey key) {
        this.ipAddress = ipAddress;
        this.socket = socket;
        this.key = key;

        bufferIn = ByteBuffer.allocate(1024);
        bufferOut = ByteBuffer.allocate(1024);
    }

    public void sendMessage(String message) {
        bufferOut.put(message.getBytes());
    }

    public void sendMessage(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        try {
            socket.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int handleRead() throws IOException {
        int bytesIn = 0;
        bytesIn = socket.read(bufferIn);
        if (bytesIn == -1) {
            throw new IOException("Socket closed");
        }
        if (bytesIn > 0) {
            bufferIn.flip();
            bufferIn.mark();

            //  TODO: Do something here with the bytes besides printing them to console
            while (bufferIn.hasRemaining()) {
                System.out.print((char) bufferIn.get());
            }
            System.out.println();
            // Do something with this value

            bufferIn.compact();
        }
        return bytesIn;
    }

    public int handleWrite() throws IOException {
        bufferOut.flip();
        int bytesOut = socket.write(bufferOut);
        bufferOut.compact();
        // If we weren't able to write the entire buffer out, make sure we alert the selector
        // so we can be notified when we are able to write more bytes to the socket
        if (bufferOut.hasRemaining()) {
            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        } else {
            key.interestOps(SelectionKey.OP_READ);
        }
        return bytesOut;
    }

    public void disconnect() {
        try {
            socket.close();
            key.cancel();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}