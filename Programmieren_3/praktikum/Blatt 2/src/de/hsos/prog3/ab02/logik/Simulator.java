package de.hsos.prog3.ab02.logik;


import com.sun.istack.internal.Nullable;
import de.hsos.prog3.ab02.test.Test;

import java.io.Console;

public class Simulator implements Simulation {

    /**
     * Das gegenwärtige Spielfeld
     */
    private boolean[][] spielfeld;
    /**
     * Die gr&ouml;&szlig;e des Spielfelds
     */
    private int anzahlFelder;

    /**
     * Das {@link BeiAenderung}-Objekt auf dem bei einer änderung des Spielfeldes ein Callback ausgef&uuml;hrt werden soll.
     */
    @Nullable
    private BeiAenderung beiAenderung;

    /**
     * Default Constructor
     */
    public Simulator() {
    }


    @Override
    public void berechneAnfangsGeneration(int anzahlFelder, int wahrscheinlichkeitDerBesiedlung) {
        this.anzahlFelder = anzahlFelder;
        spielfeld = new boolean[anzahlFelder][anzahlFelder];
        for (int x = 0; x < spielfeld.length; x++) {
            for (int y = 0; y < spielfeld[x].length; y++) {
                int n = (int) (Math.random() * 100) + 1;//[1,100]; Math.random() => [0.0,1.0);
                spielfeld[x][y] = n <= wahrscheinlichkeitDerBesiedlung;
            }
        }
        if (beiAenderung != null) {
            beiAenderung.aktualisiere(spielfeld);
        }
    }


    @Override
    public void berechneFolgeGeneration(int berechnungsschritte) {
        if (berechnungsschritte <= 1) {
            berechneFolgeGeneration();
        } else {
            for (int i = 0; i < berechnungsschritte; i++) {
                berechneFolgeGeneration();
                try {
                    Thread.sleep(150); // Anzeigedelay;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Überladung der berechneFolgeGeneration Methode ohne &Uuml;bergabeparameter;
     * Berechnet immer genau die n&auml;chste Generation
     */
    public void berechneFolgeGeneration() {
        if (spielfeld == null) {
            throw new RuntimeException("Spielfeld noch nicht initialisiert!");
        }

        boolean[][] naechstesFeld = new boolean[anzahlFelder][anzahlFelder];
        for (int x = 0; x < spielfeld.length; x++) {
            for (int y = 0; y < spielfeld[x].length; y++) {
                int nachbarn = getNachbarn(x, y);
                if (nachbarn == 3 || (getFeld(x, y) && nachbarn == 2)) {
                    naechstesFeld[x][y] = true;
                }
            }
        }
        spielfeld = naechstesFeld;
        if (beiAenderung != null) {
            beiAenderung.aktualisiere(spielfeld);
        }
    }

    /**
     * Gibt die Anzahl der bev&ouml;lkerten Nachbarn einer Zelle wieder.
     *
     * @param x X-Index des Feldes
     * @param y Y-Index des Feldes
     * @return Anzahl der bev&ouml;kerten Nachbarn.
     */
    private int getNachbarn(int x, int y) {
        int count = 0;
        for (int deltaX = -1; deltaX <= 1; deltaX++) {
            for (int deltaY = -1; deltaY <= 1; deltaY++) {
                if ((deltaX != 0 || deltaY != 0) && getFeld(x + deltaX, y + deltaY)) {
                    count++;
                }
            }
        }
        return count;
    }


    /**
     * Gibt Status der Zellen an der Position (x,y) zurück. Felder au&szlig;erhalb des Spielfeldes sind immer unbesiedelt.
     *
     * @param x X-Index des Feldes
     * @param y Y-Index des Feldes
     * @return Status der Zelle an der Position (x,y)
     */
    private boolean getFeld(int x, int y) {
        try {
            return spielfeld[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override
    public void anmeldenFuerAktualisierung(BeiAenderung beiAenderung) {
        this.beiAenderung = beiAenderung;
    }


}
