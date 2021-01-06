package de.hsos.prog3.ab4.pong.app;

import de.hsos.prog3.ab4.pong.util.Interaktionsbrett;
import processing.core.PApplet;

public class Spielfeld {
    public static final int BREITE = 850;
    public static final int HOEHE = 500;
    public static final int MARGIN = 10;

    private Rechteck spielflaeche;

    public Spielfeld() {
        spielflaeche = new Rechteck(MARGIN, MARGIN, BREITE - MARGIN, HOEHE - MARGIN);
    }

    public void darstellen(PApplet p){
        spielflaeche.darstellen(p);
        mittellineDarstellen(p);
    }

    private void mittellineDarstellen(PApplet p){
        p.line(spielflaeche.mitteInX(),spielflaeche.oben(),spielflaeche.mitteInX(),spielflaeche.unten());
    }

    public int oben(){
        return spielflaeche.oben();
    }

    public int unten(){
        return spielflaeche.unten();
    }

    public int links(){
        return spielflaeche.links();
    }

    public int rechts(){
        return spielflaeche.rechts();
    }

    public int mitteInY(){
        return spielflaeche.mitteInY();
    }

}
