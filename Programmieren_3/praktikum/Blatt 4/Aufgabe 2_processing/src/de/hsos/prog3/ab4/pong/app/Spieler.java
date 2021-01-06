package de.hsos.prog3.ab4.pong.app;

import de.hsos.prog3.ab4.pong.util.Interaktionsbrett;
import processing.core.PApplet;

public class Spieler {
    private final Spielfeld spielfeld;
    public final Rechteck schlaeger;
    private int punkte;
    private int x, y;
    private int speed;

    public Spieler(Spielfeld spielfeld, int x, int y) {
        this.spielfeld = spielfeld;
        this.schlaeger = new Rechteck(x, y, x + Spielfeld.BREITE / 100, y + Spielfeld.HOEHE / 10);
        this.speed = Spielfeld.HOEHE / 50;
        this.punkte = 0;
        this.x = x;
        this.y = y;
    }

    public void darstellen(PApplet p) {
        schlaeger.darstellen(p);
        p.text(String.valueOf(punkte),x, spielfeld.oben() + Spielfeld.MARGIN );
    }

    public void aufwaerts() {
        int dy = Math.max(y - speed, spielfeld.oben()) - y;
        schlaeger.verschiebe(0, dy);
        y += dy;
    }

    public void abwaerts() {
        int dy = Math.min(y + speed, spielfeld.unten() - schlaeger.hoehe()) - y;
        schlaeger.verschiebe(0, dy);
        y += dy;
    }

    public void erhoehePunkte() {
        punkte++;
    }

    public void setzePunkteZurueck() {
        punkte = 0;
    }

    public int getPunkte(){
        return punkte;
    }


}
