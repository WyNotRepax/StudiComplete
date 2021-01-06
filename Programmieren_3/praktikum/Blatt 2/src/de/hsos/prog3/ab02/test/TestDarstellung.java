package de.hsos.prog3.ab02.test;

import de.hsos.prog3.ab02.ui.SpielfeldDarstellung;
import de.hsos.prog3.ab02.util.Interaktionsbrett;

public class TestDarstellung {
    public static void main(String[] args) {
        boolean[][] testspielfeld = new boolean[30][30];
        testInit(testspielfeld);
        SpielfeldDarstellung testDarstellung = new SpielfeldDarstellung(new Interaktionsbrett());
        testDarstellung.spielfeldDarstellen(testspielfeld);
    }

    public static void testInit(boolean[][] spielfeld) {
        for (int x = 0; x < spielfeld.length; x++) {
            for (int y = 0; y < spielfeld[x].length; y++) {
                //spielfeld[x][y] = (x + y) % 2 == 0;
                spielfeld[x][y] = Math.random() > 0.5f;
            }
        }
    }
}


