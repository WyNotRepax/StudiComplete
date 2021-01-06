package de.hsos.prog3.ab02.test;

import de.hsos.prog3.ab02.ui.Quadrat;
import de.hsos.prog3.ab02.util.Interaktionsbrett;

public class TestQuadrat {
    public static void main(String[] args) {
        Interaktionsbrett ib = new Interaktionsbrett();
        Quadrat q1 = new Quadrat(10,10,20);
        Quadrat q2 = new Quadrat(40,10,20);
        Quadrat q3 = new Quadrat(70,10,20);
        q1.darstellenRahmen(ib);
        q2.darstellenRahmen(ib);
        q2.darstellenFuellung(ib);
        q3.darstellenFuellung(ib);

    }
}
