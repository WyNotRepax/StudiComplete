package de.hsos.prog3.ab4.basket.app;

import de.hsos.prog3.ab4.basket.util.Interaktionsbrett;

public class Ball {

    private static final int BALLRADIUS = 10;

    private final Interaktionsbrett spielfeld;
    private final Korb korb;

    int x, y;

    public Ball(Interaktionsbrett spielfeld, int x, int y, Korb korb) {
        this.x = x;
        this.y = y;
        this.spielfeld = spielfeld;
        this.korb = korb;
        spielfeld.neuerKreis(this, "Ball", x, y, BALLRADIUS);
    }

    public static Ball randomBall(Interaktionsbrett spielfeld, int width, int height, Korb korb) {
        return new Ball(spielfeld, spielfeld.zufall(BALLRADIUS, width - BALLRADIUS), spielfeld.zufall(BALLRADIUS, height - BALLRADIUS), korb);
    }

    private void updatePosition(int x, int y){
        this.x = x+BALLRADIUS;
        this.y = y+BALLRADIUS;
    }

    public Boolean mitMausAngeklickt(String name, int x, int y) {
        spielfeld.starteUhr();
        return true;
    }

    public Boolean mitMausVerschoben(String name, int x, int y) {
        updatePosition(x,y);
        return true;
    }

    public Boolean mitMausLosgelassen(String name, int x, int y) {
        updatePosition(x,y);
        if (korb.getroffen(this)) {
            spielfeld.stoppeUhr();
            int time = spielfeld.leseUhr();
            spielfeld.neuerText(0,10,"Sie haben " + time + " Millisekunden benötigt.");
        }
        return true;
    }

}
