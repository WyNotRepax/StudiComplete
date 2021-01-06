package de.hsos.prog3.ab02.logik;

/**
 * Interface für die Anmeldung von Callback bei aktualisierung des Spielfeldes im Simulator
 */

public interface BeiAenderung {
    /**
     * Callback Methode, welche bei &Auml;nderung aufgerufen wird.
     *
     * @param neu Das aktualisierte Spielfeld
     */
    void aktualisiere(boolean[][] neu);
}
