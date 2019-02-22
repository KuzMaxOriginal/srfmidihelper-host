package com.mkuzmin.srfmidihelper;

import java.io.*;

import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;

public class NativeMessagingIOTest {
    @Test
    void receiveMessage() throws IOException {
        JSONObject greetingsObject = new JSONObject();
        greetingsObject.put("greetings", "hello");

        String greetingsString = greetingsObject.toString();

        ByteArrayOutputStream inputStreamWriter = new ByteArrayOutputStream();
        inputStreamWriter.write(Utils.integerToByteArray(greetingsString.length(), 4));
        inputStreamWriter.write(greetingsString.getBytes("UTF-8"));

        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputStreamWriter.toByteArray());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        NativeMessagingIO nativeMessagingIO = new NativeMessagingIO(inputStream, outputStream);

        JSONObject receivedMessage = nativeMessagingIO.receiveMessage();
        Assertions.assertEquals("hello", receivedMessage.get("greetings"));
    }
}
