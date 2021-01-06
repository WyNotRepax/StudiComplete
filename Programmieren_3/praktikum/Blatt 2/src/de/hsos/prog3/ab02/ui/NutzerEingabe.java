package de.hsos.prog3.ab02.ui;

import de.hsos.prog3.ab02.util.EinUndAusgabe;

/**
 * Klasse zum Einlesen der Nutzereingaben
 */

public class NutzerEingabe {
    private final EinUndAusgabe io;

    /**
     * Erzeugt neue Nutzereingabe
     *
     * @param io das zu verwendendende {@link EinUndAusgabe}-Objekt
     */
    public NutzerEingabe(EinUndAusgabe io) {
        this.io = io;
    }

    /**
     * Fordert den Nutzer auf die Anzahl der Zeilen des Spielfeldes Einzugeben.
     * Stellt sicher, dass die Eingabe positiv ist.
     *
     * @return Zellenanzahl, mindestens 1
     */
    public int anzahlZeilenDesSpielfeldes() {
        int eingabe = 0;
        do {
            io.ausgeben("Anzahl der Zeilen des Spielfeldes eingeben:");
            eingabe = io.leseInteger();
        } while (eingabe <= 0);
        return eingabe;
    }

    /**
     * Fordert den Nutzer auf die Wahrscheinlichkeit der Besiedlung einer Zelle einzugeben.
     * Stellt sicher, dass die Eingabe zwischen 1 und 100 inklusive liegt.
     *
     * @return Besiedlungswahrscheinlichkeit
     * groesser gleich 1 und
     * kleiner gleich 100
     */
    public int wahrscheinlichkeitDerBesiedlung() {
        int eingabe = 0;
        do {
            io.ausgeben("Wahrscheinlichkeit der Besiedlung eingeben (1-100):");
            eingabe = io.leseInteger();
        } while (eingabe < 1 || eingabe > 100);
        return eingabe;
    }

    /**
     * Fordert den Nutzer auf die Anzahl der Simulationsschritte Einzugeben
     * @return Anzahl der Simulationsschritte niemals 0
     */
    public int anzahlDerSimulationsschritte() {
        int eingabe = 0;
        do {
            io.ausgeben("Anzahl der Simulationsschritte eingeben:");
            eingabe = io.leseInteger();
        } while (eingabe == 0);
        return eingabe;
    }

}
