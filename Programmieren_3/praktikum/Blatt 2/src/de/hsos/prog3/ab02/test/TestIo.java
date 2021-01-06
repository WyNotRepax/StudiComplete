package de.hsos.prog3.ab02.test;

import de.hsos.prog3.ab02.ui.NutzerEingabe;
import de.hsos.prog3.ab02.util.EinUndAusgabe;

public class TestIo
{
    public static void main(String[] args) {
        EinUndAusgabe io = new EinUndAusgabe();
        NutzerEingabe nutzerEingabe = new NutzerEingabe(io);
        System.out.println(nutzerEingabe.anzahlZeilenDesSpielfeldes());
        System.out.println(nutzerEingabe.wahrscheinlichkeitDerBesiedlung());
    }
}
