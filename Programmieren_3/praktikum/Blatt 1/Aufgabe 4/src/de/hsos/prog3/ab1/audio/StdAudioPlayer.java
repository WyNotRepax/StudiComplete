package de.hsos.prog3.ab1.audio;

import java.io.IOException;
import java.net.URL;

public interface StdAudioPlayer {
    void einmaligAbspielen(URL url) throws IOException;

    void wiederholtAbspielen(URL url, int wiederholungen) throws IOException;

    void tonAus();

    void tonAn();
}
