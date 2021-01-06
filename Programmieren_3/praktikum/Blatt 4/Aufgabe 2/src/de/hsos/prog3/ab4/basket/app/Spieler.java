package de.hsos.prog3.ab4.basket.app;

import de.hsos.prog3.ab4.basket.util.Interaktionsbrett;

public class Spieler {


    public enum Seite {LINKS, RECHTS}

    private static final int SPEED = 10;
    private static final String NAME = "Spieler";

    private final Interaktionsbrett ib;

    private int x, y;
    private int minY, maxY;
    private int breite, hoehe;
    private final Seite seite;


    Spieler(Interaktionsbrett ib, int x, int minY, int maxY, int breite, int hoehe, Seite seite) {
        this.ib = ib;
        this.minY = minY;
        this.maxY = maxY;
        this.breite = breite;
        this.hoehe = hoehe;
        this.x = x - breite / 2;
        this.y = (minY + maxY) / 2 - hoehe / 2;
        this.seite = seite;
        ib.neuesRechteck(this, NAME, this.x, this.y, breite, hoehe);
        ib.willTasteninfo(this);
    }

    public void tasteGedrueckt(String s) {
        if ((seite == Seite.LINKS && s.equals("a")) || (seite == Seite.RECHTS && s.equals("Oben"))) {
            y = Math.max(minY, y - SPEED);
        } else if ((seite == Seite.LINKS && s.equals("y")) || (seite == Seite.RECHTS && s.equals("Unten"))) {
            y = Math.min(maxY - hoehe, y + SPEED);
        }
        ib.verschiebeObjektNach(this, NAME, x, y);
    }




}
