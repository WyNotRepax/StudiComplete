package de.hsos.prog3.ab4.pong.app;

import de.hsos.prog3.ab4.pong.util.Interaktionsbrett;


public class PongSpiel {

    private static final int MSPF = 17;

    private enum KeyCodes {
        START("s"), EXIT("e"), SPIELER_LINKS_HOCH("a"), SPIELER_LINKS_RUNTER("y"), SPIELER_RECHTS_HOCH("Oben"), SPIELER_RECHTS_RUNTER("Unten"), SCHNELLER("+"), LANGSAMER("-");

        private String code;

        KeyCodes(String keyCode) {
            this.code = keyCode;
        }

        public boolean equals(String s) {
            return code.equals(s);
        }
    }

    private final Spielfeld spielfeld;
    private final Spieler spielerLinks, spielerRechts;
    private Ball ball;
    private final Interaktionsbrett ib;
    private boolean gestartet = false;
    private final KollisionsDetektor kollisionsDetektor;

    public PongSpiel() {
        spielfeld = new Spielfeld();
        spielerLinks = new Spieler(spielfeld, spielfeld.links() + Spielfeld.MARGIN, spielfeld.mitteInY() - Spielfeld.BREITE / 20);
        spielerRechts = new Spieler(spielfeld, spielfeld.rechts() - Spielfeld.MARGIN - Spielfeld.BREITE / 100, spielfeld.mitteInY() - Spielfeld.BREITE / 20);
        ball = new Ball(spielfeld, Spielfeld.MARGIN * 4, spielfeld.mitteInY() - 10, 20);
        kollisionsDetektor = new KollisionsDetektor(spielfeld,spielerLinks,spielerRechts);
        ib = new Interaktionsbrett();
        ib.willTasteninfo(this);
        darstellen();
    }

    public void spielen() {
        while (true) {
            long startTime = System.nanoTime();
            ball.bewegen(1);
            kollisionen();
            vergebePunkte();
            if(Math.max(spielerLinks.getPunkte(),spielerRechts.getPunkte())>15){
                System.exit(0);
            }
            darstellen();
            long deltaMSPF = MSPF - (startTime - System.nanoTime()) / 100000;
            try {
                Thread.sleep(deltaMSPF);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void darstellen() {
        ib.abwischen();
        spielfeld.darstellen(ib);
        spielerRechts.darstellen(ib);
        spielerLinks.darstellen(ib);
        ball.darstellen(ib);
    }

    private void kollisionen(){
        kollisionsDetektor.checkBeruehrungBallSpielfeldGrenzen(ball);
        kollisionsDetektor.checkBeruehrungBallMitSchlaeger(ball);
    }

    private void vergebePunkte(){
        if(kollisionsDetektor.checkAusserhalbDesSpielfeldes(ball) == BallPosition.DRAUSSEN_LINKS){
            spielerRechts.erhoehePunkte();
            ball = new Ball(spielfeld, Spielfeld.BREITE - Spielfeld.MARGIN * 4, spielfeld.mitteInY() - 10, 20);
            ball.umkehrenDerBewegungInX();
        }else if(kollisionsDetektor.checkAusserhalbDesSpielfeldes(ball) == BallPosition.DRAUSSEN_RECHTS){
            spielerLinks.erhoehePunkte();
            ball = new Ball(spielfeld, Spielfeld.MARGIN * 4, spielfeld.mitteInY() - 10, 20);
        }
    }

    public void tasteGedrueckt(String s) {
        System.out.println(s);
        if (KeyCodes.START.equals(s)) {
            if (!gestartet) {
                gestartet = true;
                new Thread(this::spielen).start();
            }
        } else if (KeyCodes.EXIT.equals(s)) {
            System.exit(0);
        } else if (KeyCodes.SPIELER_LINKS_HOCH.equals(s)) {
            spielerLinks.aufwaerts();
        } else if (KeyCodes.SPIELER_LINKS_RUNTER.equals(s)) {
            spielerLinks.abwaerts();
        } else if (KeyCodes.SPIELER_RECHTS_HOCH.equals(s)) {
            spielerRechts.aufwaerts();
        } else if (KeyCodes.SPIELER_RECHTS_RUNTER.equals(s)) {
            spielerRechts.abwaerts();
        } else if(KeyCodes.SCHNELLER.equals(s)){
            ball.schneller();
        } else if(KeyCodes.LANGSAMER.equals(s)){
            ball.langsamer();
        }

    }
}

