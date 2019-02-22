package com.mkuzmin.srfmidihelper;

import org.json.*;

import java.io.*;
import java.util.logging.Logger;

public class NativeMessagingIO {
    public static final NativeMessagingIO SYSTEM_IO = new NativeMessagingIO(System.in, System.out);

    private InputStream in;
    private OutputStream out;

    public NativeMessagingIO(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    public JSONObject receiveMessage() throws IOException {
        byte[] messageLengthBytes = new byte[4];

        if (this.in.read(messageLengthBytes) == -1) {
            return null;
        }

        int messageLength = Utils.byteArrayToInteger(messageLengthBytes);
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

        this.out.write(Utils.integerToByteArray(messageBytes.length, 4));
        this.out.write(messageBytes);
        this.out.flush();

        Logger.getLogger("").info("Sent message: " + message);
    }
}
