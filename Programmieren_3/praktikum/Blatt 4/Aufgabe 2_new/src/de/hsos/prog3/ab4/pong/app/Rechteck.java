package de.hsos.prog3.ab4.pong.app;

import de.hsos.prog3.ab4.pong.util.Interaktionsbrett;

public class Rechteck {

    private int x1, y1, x2, y2;

    public boolean gefuellt = false;

    Rechteck(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int oben() {
        return Math.min(y1, y2);
    }

    public int unten() {
        return Math.max(y1, y2);
    }

    public int links() {
        return Math.min(x1, x2);
    }

    public int rechts() {
        return Math.max(x1, x2);
    }

    public int breite() {
        return Math.abs(x1 - x2);
    }

    public int hoehe() {
        return Math.abs(y1 - y2);
    }

    public int mitteInY() {
        return (y1 + y2) / 2;
    }

    public int mitteInX() {
        return (x1 + x2) / 2;
    }

    public void verschiebe(int dx, int dy) {
        x1 += dx;
        x2 += dx;
        y1 += dy;
        y2 += dy;
    }

    public boolean ueberschneidet(Rechteck other) {
        if (
                this.rechts() < other.links() ||
                        this.links() > other.rechts() ||
                        this.unten() < other.oben() ||
                        this.oben() > other.unten()
        ) {
            return false;
        }
        return true;
    }

    public void darstellen(Interaktionsbrett ib) {
        ib.neuesRechteck(links(), oben(), breite(), hoehe());
        if (gefuellt) {
            for (int i = links() + 1; i < rechts(); i++) {
                ib.neueLinie(i, oben() + 1, i, unten() - 1);
            }
        }
    }
}
