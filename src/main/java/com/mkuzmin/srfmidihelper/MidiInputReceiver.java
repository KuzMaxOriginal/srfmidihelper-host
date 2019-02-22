package com.mkuzmin.srfmidihelper;

import javax.sound.midi.*;

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
    public void close() {
    }
}