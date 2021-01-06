package de.hsos.prog3.ab4.pong.app;

public class KollisionsDetektor {
    private final Spielfeld spielfeld;
    private final Spieler spielerLinks, spielerRechts;

    public KollisionsDetektor(Spielfeld spielfeld, Spieler spielerLinks, Spieler spielerRechts) {

        this.spielfeld = spielfeld;
        this.spielerLinks = spielerLinks;
        this.spielerRechts = spielerRechts;
    }
    public void checkBeruehrungBallSpielfeldGrenzen(Ball ball){
        if(ball.form.unten() > spielfeld.unten() || ball.form.oben() < spielfeld.oben() ){
            ball.umkehrenDerBewegungInY();
        }
    }

    public void checkBeruehrungBallMitSchlaeger(Ball ball){
        if( spielerLinks.schlaeger.ueberschneidet(ball.form) || spielerRechts.schlaeger.ueberschneidet(ball.form)){
            ball.umkehrenDerBewegungInX();
            if(Math.random() > 0.5){
                ball.umkehrenDerBewegungInY();
            }
        }
    }

    BallPosition checkAusserhalbDesSpielfeldes(Ball ball) {
        if(ball.form.rechts() < spielfeld.links()){
            return BallPosition.DRAUSSEN_LINKS;
        }
        else if(ball.form.links() > spielfeld.rechts()){
            return BallPosition.DRAUSSEN_RECHTS;
        }
        return BallPosition.DRINNEN;
    }
}
