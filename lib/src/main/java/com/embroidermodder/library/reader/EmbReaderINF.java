package com.embroidermodder.library.reader;

import com.embroidermodder.library.EmbThread;

import java.io.IOException;

public class EmbReaderINF extends EmbReader {
    @Override
    protected void read() throws IOException {
        skip(12);
        int numberOfColors = readInt32BE();
        for (int x = 0; x < numberOfColors; x++) {
            skip(4);
            EmbThread t = new EmbThread();
            int red = readInt8();
            int green = readInt8();
            int blue = readInt8();
            t.setColor(red, green, blue);
            skip(2);
            t.setCatalogNumber(readString(50));
            t.setDescription(readString(50));
            pattern.addThread(t);
        }
    }
}
