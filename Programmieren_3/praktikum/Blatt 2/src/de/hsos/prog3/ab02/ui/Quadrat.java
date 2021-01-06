package de.hsos.prog3.ab02.ui;

import de.hsos.prog3.ab02.util.Interaktionsbrett;

/**
 * Klasse zum Anzeigen von gef&uuml;llten und ungef&uuml;llten Quadraten auf einem {@link Interaktionsbrett}
 */

public class Quadrat {
    /**
     * x-Koordinate des Quadrats
     */
    private final int x;
    /**
     * y-Koordinate des Quadrats
     */
    private final int y;

    /**
     * Seitenl&auml;nge des Quadrats
     */
    private final int seitenlaenge;

    /**
     * Erzeugt ein neues Quadrat.
     * Das neue Quadrat wird noch nicht Angezeigt.
     *
     * @param x            Abstand von der linken oberen Ecke des Quadrats
     *                     zur linken Kante des Interaktionsbrettes
     * @param y            Abstand von der linken oberen Ecke des Quadrats
     *                     zur oberen Kante des Interaktionsbrettes
     * @param seitenlaenge Seitenlaenge des Quadrats
     */
    public Quadrat(int x, int y, int seitenlaenge) {
        this.x = x;
        this.y = y;
        this.seitenlaenge = seitenlaenge;
    }

    /**
     * Stellt den Rahmen des Quadrates auf dem uebergebenen Interaktionsbrett dar
     *
     * @param ib {@link Interaktionsbrett} auf dem das Quadrat abgebildet wird
     */
    public void darstellenRahmen(Interaktionsbrett ib) {
        ib.neuesRechteck(x, y, seitenlaenge, seitenlaenge);
    }

    /**
     * Stellt die Fuellung des Quadrates auf dem uebergebenen Interaktionsbrett dar
     *
     * @param ib {@link Interaktionsbrett} auf dem das Quadrat abgebildet wird
     */
    public void darstellenFuellung(Interaktionsbrett ib) {
        for (int deltaX = 1; deltaX < seitenlaenge; deltaX++) {
            ib.neueLinie(x + deltaX, y + 1, x + deltaX, y + seitenlaenge - 1);
        }
    }
}
