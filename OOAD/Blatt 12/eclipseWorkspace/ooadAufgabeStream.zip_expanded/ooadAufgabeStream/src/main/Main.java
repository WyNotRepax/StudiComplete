package main;

import analytics.Analyse;
import analytics.AnalyseImpl;


public class Main {

    public static void main(String[] args) {
        // TODO: hier Objekt der erstellten Klasse ergaenzen: a = new ...
        Analyse a = new AnalyseImpl();
        System.out.println("---anzahlPruefungen");
        System.out.println(a.anzahlPruefungen(a.lesePruefungen()));
        System.out.println("---anzahlBestandenerPruefungen");
        System.out.println("101: " + a
                .anzahlBestandenerPruefungen(a.lesePruefungen(), 101));
        System.out.println("103: " + a
                .anzahlBestandenerPruefungen(a.lesePruefungen(), 103));
        System.out.println("---durchschnittsnote");
        System.out.println("102: " + a
                .durchschnittsnote(a.lesePruefungen(), 102));
        System.out.println("---durchschnittModul");
        System.out.println("Programmierung 1: " + a
                .durchschnittModul(a.lesePruefungen(), "Programmierung 1"));
        System.out.println("SW-Projekt: " + a
                .durchschnittModul(a.lesePruefungen(), "SW-Projekt"));
        System.out.println("---endgueltigDurchgefallen");
        System.out.println(a
                .endgueltigDurchgefallen(a.lesePruefungen()));
        System.out.println("---hatNochOffenenDrittenVersuch");
        System.out.println(a
                .hatNochOffenenDrittenVersuch(a.lesePruefungen()));
        System.out.println("---zeigePruefungenVon");
        a.zeigePruefungenVon(a.lesePruefungen(), 103);
        System.out.println("---zeigeBestandenePruefungenVon");
        a.zeigeBestandenePruefungenVon(a.lesePruefungen(), 103);
        System.out.println("---zeigeGeordnetPruefungenVon");
        a.zeigeGeordnetPruefungenVon(a.lesePruefungen(), 103);
        System.out.println("---zeigeDetailgeordnetPruefungenVon");
        a.zeigeDetailgeordnetPruefungenVon(a.lesePruefungen(), 103);
        System.out.println("------");
    }
}
