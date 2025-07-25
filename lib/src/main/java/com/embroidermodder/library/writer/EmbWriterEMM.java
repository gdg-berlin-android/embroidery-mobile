package com.embroidermodder.library.writer;

import com.embroidermodder.library.EmbThread;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EmbWriterEMM extends EmbWriter {
    public static final int MAGIC_NUMBER = 0xE3B830DD; //EMBRMODD
    public static final int VERSION = 1;

    @Override
    public void write() throws IOException {
        writeInt32LE(MAGIC_NUMBER);
        writeInt32LE(VERSION);
        writeVersion1();
        pattern.getStitches().write(stream);
    }

    public void writeVersion1() throws IOException {
        int threadcount = pattern.getThreadCount();
        writeInt32LE(threadcount);
        if (threadcount != 0) {
            for (EmbThread thread : pattern.getThreadlist()) {
                writeThread(thread);
            }
        }
        HashMap<String, String> metadata = pattern.getMetadata();
        writeMap(metadata);
    }

    public void writeThread(EmbThread thread) throws IOException {
        writeInt32(thread.getColor());
        HashMap<String, String> metadata = thread.getMetadata();
        writeMap(metadata);
    }

    public void writeMap(Map<String, String> map) throws IOException {
        byte[] bytes;
        writeInt32LE(map.size());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            bytes = entry.getKey().getBytes();
            writeInt32LE(bytes.length);
            write(bytes);
            bytes = entry.getValue().getBytes();
            writeInt32LE(bytes.length);
            write(bytes);
        }
    }
}
