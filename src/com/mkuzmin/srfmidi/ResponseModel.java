package com.mkuzmin.srfmidi;

import org.json.*;

import java.io.*;
import java.util.*;

public class ResponseModel {
    private static void sendResponse(JSONObject response) {
        try {
            NativeMessagingIO.getInstance().sendMessage(response);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void pong() {
        JSONObject response = new JSONObject();
        response.put("type", "pong");

        sendResponse(response);
    }

    public static void devicesList(Collection<?> devicesIndexes) {
        JSONArray responseDevicesList = new JSONArray(devicesIndexes);

        JSONObject response = new JSONObject();
        response.put("type", "devices_list");
        response.put("devices", responseDevicesList);

        sendResponse(response);
    }

    public static void midiMessage(String deviceIndex, byte[] midiMessage) {
        JSONObject response = new JSONObject();
        response.put("type", "midi_message");
        response.put("device_index", deviceIndex);
        response.put("message", Base64.getEncoder().encodeToString(midiMessage));

        sendResponse(response);
    }
}
