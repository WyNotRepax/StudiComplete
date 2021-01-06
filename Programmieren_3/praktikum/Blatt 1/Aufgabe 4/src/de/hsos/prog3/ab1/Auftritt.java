package de.hsos.prog3.ab1;

import de.hsos.prog3.ab1.audio.StdAudioPlayer;
import de.hsos.prog3.ab1.audio.adapter.SimpleAudioPlayerAdapter;

import java.io.IOException;

public class Auftritt implements Verhalten {
    Auftritt() {
    }

    @Override
    public void spielen(Orchester orchester) {
        StdAudioPlayer player = new SimpleAudioPlayerAdapter();
        try {
            player.einmaligAbspielen(orchester.getAudioDateiKonzert());
        } catch (IOException e) {
            System.err.println("Konzert wird Abgebrochen");
        }
    }
}
