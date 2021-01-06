package de.hsos.prog3.ab4.basket.app;

import de.hsos.prog3.ab4.basket.util.Interaktionsbrett;

public class Spielfeld {

    private static final int OFFSET = 10;

    private final Interaktionsbrett ib;
    private final int width, height;

    private Spieler spieler1;
    private Spieler spieler2;
    private Ball ball;

    public Spielfeld(Interaktionsbrett ib, int width, int height) {
        this.width = width;
        this.height = height;
        this.ib = ib;
        this.zeichneHintergrund();
        this.spieler1 = new Spieler(ib, 2 * OFFSET, OFFSET, height + OFFSET, 10, 100, Spieler.Seite.LINKS);
        this.spieler2 = new Spieler(ib, width, OFFSET, height + OFFSET, 10, 100, Spieler.Seite.RECHTS);
        this.ball = new Ball(ib, OFFSET + width / 2, OFFSET + height / 2, 20, 20, 500, OFFSET, OFFSET + height);
        ib.willTasteninfo(this);
    }

    private void zeichneHintergrund() {
        ib.neueLinie(OFFSET, OFFSET, OFFSET + width, OFFSET); //OBERE LINIE
        ib.neueLinie(OFFSET, OFFSET, OFFSET, OFFSET + height); //LINKE LININE
        ib.neueLinie(OFFSET + width, OFFSET, OFFSET + width, OFFSET + height); //RECHTE LINIE
        ib.neueLinie(OFFSET, OFFSET + height, OFFSET + width, OFFSET + height); //UNTERE LINIE
        ib.neueLinie(OFFSET + width / 2, OFFSET, OFFSET + height, OFFSET + width / 2); //MITTLERE LINE
    }

    public void update(int delta) {
        ball.update(delta);
    }

    public void tasteGedrueckt(String s) {
        if(s.equals("r")){
            ball.reset();
        }
        else if(s.equals("e")){
            System.exit(1);
        }
    }
}
