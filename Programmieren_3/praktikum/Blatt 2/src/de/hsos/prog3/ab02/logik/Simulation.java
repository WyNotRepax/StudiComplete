package de.hsos.prog3.ab02.logik;

/**
 * Interface für Simulatoren
 */

public interface Simulation {
    /**
     * Berechnet den Initialzustand der Simulation
     *
     * @param anzahlFelder                    Breite und Hoehe des Spielfeldes in Feldern
     * @param wahrscheinlichkeitDerBesiedlung Wahrscheinlichkeit der Besiedlung eines Feldes in %
     */
    void berechneAnfangsGeneration(int anzahlFelder, int wahrscheinlichkeitDerBesiedlung);

    /**
     * Berechnet eine beliebige Anzahl von Folgegenerationen
     *
     * @param berechnungsschritte Anzahl der Folgegenerationen
     */
    void berechneFolgeGeneration(int berechnungsschritte);

    /**
     * Meldet ein beiAenderung Objekt fuer einen Callback bei Aktualisierung des Spielfeldes an.
     * @param beiAenderung Das Objekt, auf welchem der Callback aufgerufen werden soll.
     */
    void anmeldenFuerAktualisierung(BeiAenderung beiAenderung);

}
