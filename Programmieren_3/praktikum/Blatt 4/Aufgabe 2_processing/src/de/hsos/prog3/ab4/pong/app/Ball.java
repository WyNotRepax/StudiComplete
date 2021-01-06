package de.hsos.prog3.ab4.pong.app;

import de.hsos.prog3.ab4.pong.util.Interaktionsbrett;
import processing.core.PApplet;

public class Ball {
    public final Rechteck form;
    private int bewegungInXProFrame, bewegungInYProFrame;
    private final Spielfeld spielfeld;
    private int x, y;

    public Ball(Spielfeld spielfeld, int x, int y, int groesse) {
        bewegungInXProFrame = 4;
        bewegungInYProFrame = 1;
        this.spielfeld = spielfeld;
        form = new Rechteck(x - groesse / 2, y - groesse / 2, x + groesse / 2, y + groesse / 2);
        form.gefuellt = true;
    }

    void bewegen(int anzahlFrames) {
        form.verschiebe(bewegungInXProFrame * anzahlFrames, bewegungInYProFrame * anzahlFrames);
    }


    public void darstellen(PApplet p) {
        form.darstellen(p);
    }

    public void umkehrenDerBewegungInX() {
        bewegungInXProFrame *= -1;
    }

    public void umkehrenDerBewegungInY() {
        bewegungInYProFrame *= -1;
    }

    public void schneller(){
        bewegungInXProFrame *= 2;
        bewegungInYProFrame *= 2;
    }

    public void langsamer() {
        bewegungInXProFrame /= 2;
        bewegungInYProFrame /= 2;
    }
}
