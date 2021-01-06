package de.hsos.prog3.ab1;

import de.hsos.prog3.ab1.audio.StdAudioPlayer;
import de.hsos.prog3.ab1.audio.adapter.SimpleAudioPlayerAdapter;

import java.io.IOException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        URL url = Main.class.getResource("/Baritone.wav");
        StdAudioPlayer player = new SimpleAudioPlayerAdapter();
        player.tonAn();
        try {
            player.einmaligAbspielen(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
