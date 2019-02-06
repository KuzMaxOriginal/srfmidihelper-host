package com.mkuzmin.srfmidihelper;

import org.json.*;

import java.io.*;
import java.nio.*;
import java.util.logging.Logger;

public class NativeMessagingIO {
    private static NativeMessagingIO instance = new NativeMessagingIO(System.in, System.out);

    private InputStream in;
    private OutputStream out;

    private NativeMessagingIO(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    public static NativeMessagingIO getInstance() { return instance; }

    public synchronized JSONObject receiveMessage() throws IOException {
        byte[] messageLengthBytes = new byte[4];

        if (this.in.read(messageLengthBytes) == -1) {
            return null;
        }

        int messageLength = ByteBuffer.wrap(messageLengthBytes).order(ByteOrder.nativeOrder()).getInt();
        byte[] messageBytes = new byte[messageLength];

        if (this.in.read(messageBytes) == -1) {
            return null;
        }

        String message = new String(messageBytes, "UTF-8");

        Logger.getLogger("").info("Received message: " + message);

        return new JSONObject(message);
    }

    public void sendMessage(JSONObject messageObject) throws IOException {
        String message = messageObject.toString();
        byte[] messageBytes = message.getBytes("UTF-8");

        this.out.write(ByteBuffer.allocate(4).order(ByteOrder.nativeOrder()).putInt(messageBytes.length).array());
        this.out.write(messageBytes);
        this.out.flush();

        Logger.getLogger("").info("Sent message: " + message);
    }
}
