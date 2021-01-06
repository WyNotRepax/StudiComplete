package de.hsos.prog3.ab4.basket.app;

import de.hsos.prog3.ab4.basket.util.Interaktionsbrett;

public class App {
    private static final int DELAY = 17;

    public static void main(String[] args) {
        Interaktionsbrett ib = new Interaktionsbrett();
        Spielfeld spielfeld = new Spielfeld(ib,1200, 600);
        int lastUpdate = (int)(System.nanoTime()/1000000);
        while (true){
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int nextUpdate = (int)(System.nanoTime()/1000000);
            spielfeld.update(nextUpdate-lastUpdate);
            lastUpdate = nextUpdate;
        }
    }
}
