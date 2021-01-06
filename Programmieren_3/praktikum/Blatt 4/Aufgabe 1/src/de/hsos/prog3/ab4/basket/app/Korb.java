package de.hsos.prog3.ab4.basket.app;

import de.hsos.prog3.ab4.basket.util.Interaktionsbrett;

public class Korb {
    public static final int KORBGROESSE = 50;

    private final int x, y;

    public Korb(Interaktionsbrett spielfeld, int x, int y) {
        this.x = x;
        this.y = y;
        spielfeld.neuesRechteck(x, y, KORBGROESSE, KORBGROESSE);
    }

    public static Korb randomKorb(Interaktionsbrett spielfeld, int width, int height){
        return new Korb(spielfeld,spielfeld.zufall(0,width-KORBGROESSE),spielfeld.zufall(0,height-KORBGROESSE));
    }

    public boolean getroffen(Ball ball) {
        return (ball.x > x && ball.y > y && ball.x < x + KORBGROESSE && ball.y < y + KORBGROESSE);
    }
}
