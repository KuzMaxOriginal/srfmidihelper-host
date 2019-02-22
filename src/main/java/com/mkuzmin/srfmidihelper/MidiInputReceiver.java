package com.mkuzmin.srfmidihelper;

import org.json.*;

import javax.sound.midi.*;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class MidiInputReceiver implements Receiver {
    private String deviceIndex;

    public MidiInputReceiver(String deviceIndex) {
        this.deviceIndex = deviceIndex;
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        ResponseModel.midiMessage(this.deviceIndex, message.getMessage());
    }

    @Override
    public void close() { }
}