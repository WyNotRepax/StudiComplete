package de.hsos.prog3.ab1;

import de.hsos.prog3.ab1.audio.StdAudioPlayer;
import de.hsos.prog3.ab1.audio.adapter.SimpleAudioPlayerAdapter;
import de.hsos.prog3.audio.SimpleAudioPlayer;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;

public class Orchester {
    private String bezeichnung;
    private DirigentIn dirigentIn;
    private Collection<MusikerIn> musikerInnen = new HashSet<>();
    private String audioDateiKonzert;
    private Verhalten verhalten;

    Orchester(String bezeichnung, String audioDateiKonzert) {
        this.bezeichnung = bezeichnung;
        this.audioDateiKonzert = audioDateiKonzert;
    }

    public void addDirigentIn(DirigentIn dirigentIn) {
        if (this.dirigentIn == null) {
            this.dirigentIn = dirigentIn;
        }
    }

    public void addMusikerIn(MusikerIn musikerIn) {
        if (musikerIn != null) {
            musikerInnen.add(musikerIn);
        }
    }

    public Collection<MusikerIn> getMusikerInnen() {
        return musikerInnen;
    }

    public URL getAudioDateiKonzert() {
        return this.getClass().getResource(audioDateiKonzert);
    }

    public void proben() {
        verhalten = new Proben();
    }

    public void auftreten() {
        verhalten = new Auftritt();
    }


    public void spielen() {
        if (verhalten != null) {
            verhalten.spielen(this);
        }
    }

    private class Proben implements Verhalten {
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

    ;

    private class Auftritt implements Verhalten {

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


    /*
    private interface Verhalten {
        void spielen(Orchester orchester);
    }
    */

}
