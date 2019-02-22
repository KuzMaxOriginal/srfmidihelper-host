package com.mkuzmin.srfmidihelper;

import java.nio.*;

public class Utils {
    public static int byteArrayToInteger(byte[] bytes) {
        return ByteBuffer.wrap(bytes).order(ByteOrder.nativeOrder()).getInt();
    }

    public static byte[] integerToByteArray(int integer, int length) {
        return ByteBuffer.allocate(length).order(ByteOrder.nativeOrder()).putInt(integer).array();
    }
}
