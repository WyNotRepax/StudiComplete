package de.hsos.prog3.ab02.ui;

import de.hsos.prog3.ab02.util.Interaktionsbrett;

/**
 * Klasse zur Darstellung des Spielfeldes
 */
public class SpielfeldDarstellung {
    private final Interaktionsbrett ib;
    private static final int SEITENLAENGE = 300;
    private static final int MARGIN = 10;
    private static final int EFFECTIVESIZE = SEITENLAENGE - 2 * MARGIN;

    /**
     * Erzeugt neue Spielfelddarstellung
     *
     * @param ib das zu verwendendende Interaktionsbrett-Objekt
     */
    public SpielfeldDarstellung(Interaktionsbrett ib) {
        this.ib = ib;
    }

    /**
     * Stellt das uebergebene Spielfeld auf dem Interaktionsbrett dar
     *
     * @param spielfeld das darzustellende Spielfeld
     */

    public void spielfeldDarstellen(boolean[][] spielfeld) {
        for (int x = 0; x < spielfeld.length; x++) {
            for (int y = 0; y < spielfeld[x].length; y++) {
                int xPos = MARGIN + EFFECTIVESIZE / spielfeld.length * x;
                int yPos = MARGIN + EFFECTIVESIZE / spielfeld[x].length * y;
                Quadrat q = new Quadrat(xPos, yPos, EFFECTIVESIZE / spielfeld.length);
                q.darstellenRahmen(ib);
                if (spielfeld[x][y]) {
                    q.darstellenFuellung(ib);
                }
            }
        }
    }

    /**
     * Leert das Interaktionsbrett
     */
    public void abwischen() {
        ib.abwischen();
    }


}
