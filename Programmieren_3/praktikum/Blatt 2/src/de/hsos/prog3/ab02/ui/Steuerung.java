package de.hsos.prog3.ab02.ui;

import de.hsos.prog3.ab02.logik.BeiAenderung;
import de.hsos.prog3.ab02.logik.Simulation;
import de.hsos.prog3.ab02.logik.Simulator;
import de.hsos.prog3.ab02.util.EinUndAusgabe;
import de.hsos.prog3.ab02.util.Interaktionsbrett;


/**
 * Klasse zur Steuerung des Spiels
 */
public class Steuerung implements BeiAenderung {

    private final NutzerEingabe nutzerEingabe;
    private final SpielfeldDarstellung spielfeldDarstellung;
    private final Simulation simulator;

    /**
     * Erzeugt neue Steuerung
     */
    public Steuerung() {
        spielfeldDarstellung = new SpielfeldDarstellung(new Interaktionsbrett());
        nutzerEingabe = new NutzerEingabe(new EinUndAusgabe());
        simulator = new Simulator();
        simulator.anmeldenFuerAktualisierung(this);
    }

    /**
     * Fragt den nutzer nach den Initialisierungsparametern und startet dann das Spiel
     */
    public void startDesSpiels() {
        int anzahlZellen = nutzerEingabe.anzahlZeilenDesSpielfeldes();
        int wahrscheinlichkeitDerBesiedlung = nutzerEingabe.wahrscheinlichkeitDerBesiedlung();
        simulator.berechneAnfangsGeneration(anzahlZellen, wahrscheinlichkeitDerBesiedlung);
        while (true) {
            int eingabe = nutzerEingabe.anzahlDerSimulationsschritte();
            if (eingabe < 0) {
                break;
            }
            simulator.berechneFolgeGeneration(eingabe);
        }

    }

    @Override
    public void aktualisiere(boolean[][] neu) {
        spielfeldDarstellung.abwischen();
        spielfeldDarstellung.spielfeldDarstellen(neu);
    }
}
