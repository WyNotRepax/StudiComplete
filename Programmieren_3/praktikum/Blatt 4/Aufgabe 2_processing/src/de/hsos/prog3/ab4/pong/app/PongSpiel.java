package de.hsos.prog3.ab4.pong.app;

import processing.core.PApplet;


public class PongSpiel extends PApplet {

    private Spielfeld spielfeld;
    private Spieler spielerLinks, spielerRechts;
    private Ball ball;
    private boolean gestartet = false;
    private KollisionsDetektor kollisionsDetektor;

    public PongSpiel() {
        super();
        spielfeld = new Spielfeld();
        spielerLinks = new Spieler(spielfeld, spielfeld.links() + Spielfeld.MARGIN, spielfeld.mitteInY() - Spielfeld.BREITE / 20);
        spielerRechts = new Spieler(spielfeld, spielfeld.rechts() - Spielfeld.MARGIN - Spielfeld.BREITE / 100, spielfeld.mitteInY() - Spielfeld.BREITE / 20);
        ball = new Ball(spielfeld, Spielfeld.MARGIN * 4, spielfeld.mitteInY() - 10, 20);
        kollisionsDetektor = new KollisionsDetektor(spielfeld, spielerLinks, spielerRechts);
    }

    public void settings() {
        size(850, 500);
    }


    public void draw() {
        if (gestartet) {
            ball.bewegen(1);
            kollisionen();
            vergebePunkte();
            if (Math.max(spielerLinks.getPunkte(), spielerRechts.getPunkte()) > 15) {
                System.exit(0);
            }
        }
        darstellen();
    }

    private void darstellen() {
        background(255);
        spielfeld.darstellen(this);
        spielerRechts.darstellen(this);
        spielerLinks.darstellen(this);
        ball.darstellen(this);
    }

    private void kollisionen() {
        kollisionsDetektor.checkBeruehrungBallSpielfeldGrenzen(ball);
        kollisionsDetektor.checkBeruehrungBallMitSchlaeger(ball);
    }

    private void vergebePunkte() {
        if (kollisionsDetektor.checkAusserhalbDesSpielfeldes(ball) == BallPosition.DRAUSSEN_LINKS) {
            spielerRechts.erhoehePunkte();
            ball = new Ball(spielfeld, Spielfeld.BREITE - Spielfeld.MARGIN * 4, spielfeld.mitteInY() - 10, 20);
            ball.umkehrenDerBewegungInX();
        } else if (kollisionsDetektor.checkAusserhalbDesSpielfeldes(ball) == BallPosition.DRAUSSEN_RECHTS) {
            spielerLinks.erhoehePunkte();
            ball = new Ball(spielfeld, Spielfeld.MARGIN * 4, spielfeld.mitteInY() - 10, 20);
        }
    }


    public void keyPressed() {
        if (key == 's') {
            if (!gestartet) {
                System.out.println("START");
                gestartet = true;
            }
        } else if (key == 'e') {
            System.exit(0);
        } else if (key == 'a') {
            spielerLinks.aufwaerts();
        } else if (key == 'y') {
            spielerLinks.abwaerts();
        } else if (key == CODED && keyCode == UP) {
            spielerRechts.aufwaerts();
        } else if (key == CODED && keyCode == DOWN) {
            spielerRechts.abwaerts();
        } else if (key == '+') {
            ball.schneller();
        } else if (key == '-') {
            ball.langsamer();
        }

    }
}

