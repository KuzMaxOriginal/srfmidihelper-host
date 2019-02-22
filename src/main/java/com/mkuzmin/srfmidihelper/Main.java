package com.mkuzmin.srfmidihelper;

import org.apache.commons.cli.*;
import org.json.*;

import javax.sound.midi.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;

public class Main {
    private static HashMap<String, MidiDevice> midiDeviceInfos = new HashMap<>();
    private static MidiDevice listeningMidiDevice;

    private static void updateMidiDeviceInfos() {
        try {
            midiDeviceInfos.clear();
            MidiDevice.Info[] midiDeviceInfosArray = MidiSystem.getMidiDeviceInfo();

            for (MidiDevice.Info midiDeviceInfo : midiDeviceInfosArray) {
                MidiDevice midiDevice = MidiSystem.getMidiDevice(midiDeviceInfo);

                if (midiDevice.getMaxTransmitters() != 0) {
                    midiDeviceInfos.put(midiDeviceInfo.getName() + " - " + midiDeviceInfo.getDescription(), midiDevice);
                }
            }
        } catch (MidiUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    private static void setupLogging(String logsPath, boolean enableLogging) throws IOException {
        for (Handler handler : Logger.getLogger("").getHandlers()) {
            Logger.getLogger("").removeHandler(handler);
        }

        if (!enableLogging) {
            return;
        }

        if (!logsPath.endsWith("/") && !logsPath.endsWith("\\")) {
            logsPath += File.separator;
        }

        File logsDirectory = new File(logsPath);
        if (!logsDirectory.exists()) {
            logsDirectory.mkdir();
        }

        FileHandler fileHandler = new FileHandler(logsPath + System.currentTimeMillis() + ".log");
        fileHandler.setFormatter(new SimpleFormatter());

        Logger.getLogger("").addHandler(fileHandler);
    }

    private static void startMainThread() {
        new Thread(() -> {
            while (true) {
                try {
                    JSONObject message = NativeMessagingIO.SYSTEM_IO.receiveMessage();

                    if (message != null) {
                        switch (message.getString("cmd")) {
                            case "ping":
                                ResponseModel.pong();
                                break;
                            case "list_devices":
                                updateMidiDeviceInfos();
                                ResponseModel.devicesList(midiDeviceInfos.keySet());
                                break;
                            case "listen_device":
                                try {
                                    updateMidiDeviceInfos();

                                    if (listeningMidiDevice != null) {
                                        listeningMidiDevice.close();
                                    }

                                    if (!message.isNull("device_index")) {
                                        String deviceIndex = message.getString("device_index");

                                        if (midiDeviceInfos.containsKey(deviceIndex)) {
                                            listeningMidiDevice = midiDeviceInfos.get(deviceIndex);

                                            Transmitter transmitter = listeningMidiDevice.getTransmitter();

                                            transmitter.setReceiver(new MidiInputReceiver(deviceIndex));
                                            listeningMidiDevice.open();
                                        }
                                    }
                                } catch (MidiUnavailableException ex) {
                                    ex.printStackTrace();
                                }

                                break;
                        }
                    } else {
                        System.exit(0);
                    }

                    Thread.sleep(50);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) throws IOException, ParseException {
        Options commandLineOptions = new Options();

        commandLineOptions.addOption(Option.builder("d")
                .longOpt("debug")
                .build());

        CommandLine commandLine = (new DefaultParser()).parse(commandLineOptions, args, false);

        setupLogging("log", commandLine.hasOption("-d") || commandLine.hasOption("--debug"));
        startMainThread();
    }
}
