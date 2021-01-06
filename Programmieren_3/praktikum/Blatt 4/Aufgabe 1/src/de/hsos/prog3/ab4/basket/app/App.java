package de.hsos.prog3.ab4.basket.app;

import de.hsos.prog3.ab4.basket.util.Interaktionsbrett;

public class App {
    private static final int WIDTH = 380;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        Interaktionsbrett spielfeld = new Interaktionsbrett();
        Korb korb = Korb.randomKorb(spielfeld, WIDTH, HEIGHT);
        Ball ball = new Ball(spielfeld, 100, 100, korb);
    }
}
