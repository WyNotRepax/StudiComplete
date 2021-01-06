package de.hsos.prog3.ab1.audio.adapter;

import de.hsos.prog3.ab1.audio.StdAudioPlayer;
import de.hsos.prog3.audio.SimpleAudioPlayer;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.URL;

public class SimpleAudioPlayerAdapter implements StdAudioPlayer {
    private boolean ton = true;

    @Override
    public void einmaligAbspielen(URL url) throws IOException {
        SimpleAudioPlayer player = new SimpleAudioPlayer(url);
        player.setDebug(!ton);
        player.verboseLogging(true);
        player.play(0);
    }

    @Override
    public void wiederholtAbspielen(URL url, int wiederholungen) throws IOException {
        SimpleAudioPlayer player = new SimpleAudioPlayer(url);
        player.setDebug(!ton);
        player.verboseLogging(true);
        player.play(wiederholungen);
    }

    @Override
    public void tonAus() {
        ton = false;
    }

    @Override
    public void tonAn() {
        ton = true;
    }
}
