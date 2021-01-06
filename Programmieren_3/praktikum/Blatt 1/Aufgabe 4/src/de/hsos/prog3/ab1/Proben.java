package de.hsos.prog3.ab1;

import de.hsos.prog3.ab1.audio.StdAudioPlayer;
import de.hsos.prog3.ab1.audio.adapter.SimpleAudioPlayerAdapter;

import java.io.IOException;
import java.net.URL;

class Proben implements Verhalten {
    Proben() {
    }

    @Override
    public void spielen(Orchester orchester) {
        StdAudioPlayer player = new SimpleAudioPlayerAdapter();
        for (MusikerIn musikerIn : orchester.getMusikerInnen()) {
            URL url = this.getClass().getResource(musikerIn.getInstrument().getAudiodatei());
            try {
                player.einmaligAbspielen(url);
            } catch (IOException e) {
                System.err.println("Probe wird Abgebrochen");
            }
        }
    }
}
