package com.mkuzmin.srfmidihelper;

import java.io.*;

import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;

public class NativeMessagingIOTest {
    private final JSONObject greetingsObject = new JSONObject();

    public NativeMessagingIOTest() {
        greetingsObject.put("greetings", "hello");
    }

    private byte[] prepareByteArray(String data) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(Utils.integerToByteArray(data.length(), 4));
        outputStream.write(data.getBytes("UTF-8"));

        return outputStream.toByteArray();
    }

    @Test
    void receiveMessage() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(prepareByteArray(greetingsObject.toString()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        NativeMessagingIO nativeMessagingIO = new NativeMessagingIO(inputStream, outputStream);

        JSONObject receivedMessage = nativeMessagingIO.receiveMessage();
        Assertions.assertEquals("hello", receivedMessage.get("greetings"));
    }

    @Test
    void sendMessage() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[0]);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        NativeMessagingIO nativeMessagingIO = new NativeMessagingIO(inputStream, outputStream);

        nativeMessagingIO.sendMessage(greetingsObject);

        Assertions.assertArrayEquals(prepareByteArray(greetingsObject.toString()), outputStream.toByteArray());
    }
}
