package com.mkuzmin.srfmidi;

import org.json.*;

import javax.sound.midi.*;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class MidiInputReceiver implements Receiver {
    private NativeMessagingIO messagingIO;
    private String deviceIndex;

    public MidiInputReceiver(NativeMessagingIO messagingIO, String deviceIndex) {
        this.messagingIO = messagingIO;
        this.deviceIndex = deviceIndex;
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        ResponseModel.midiMessage(this.deviceIndex, message.getMessage());
    }

    @Override
    public void close() { }
}