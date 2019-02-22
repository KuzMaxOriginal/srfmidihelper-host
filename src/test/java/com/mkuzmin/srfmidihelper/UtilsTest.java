package com.mkuzmin.srfmidihelper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class UtilsTest {
    @Test
    void bytesToMessageLength() {
        final int messageLength = 66;
        byte[] messageLengthBytes = ByteBuffer.allocate(4).order(ByteOrder.nativeOrder()).putInt(messageLength).array();

        Assertions.assertEquals(messageLength, Utils.byteArrayToInteger(messageLengthBytes));
    }

    @Test
    void messageLengthToBytes() {
        final int messageLength = 66;
        final int bytesArrLength = 4;
        byte[] messageLengthBytes = ByteBuffer.allocate(4).order(ByteOrder.nativeOrder()).putInt(messageLength).array();

        Assertions.assertArrayEquals(messageLengthBytes, Utils.integerToByteArray(messageLength, bytesArrLength));
    }
}
