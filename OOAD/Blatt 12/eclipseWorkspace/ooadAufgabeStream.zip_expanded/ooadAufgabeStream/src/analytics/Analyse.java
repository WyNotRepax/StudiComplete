package analytics;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import entities.Pruefung;

public interface Analyse {

    public static String DATEI = "./data.xml";

    public abstract void zeigePruefungenVon(Stream<Pruefung> pruefungen, int mat);

    public abstract void zeigeBestandenePruefungenVon(Stream<Pruefung> pruefungen, int mat);

    public abstract void zeigeGeordnetPruefungenVon(Stream<Pruefung> pruefungen, int mat);

    public abstract void zeigeDetailgeordnetPruefungenVon(Stream<Pruefung> pruefungen, int mat);

    public abstract long anzahlPruefungen(Stream<Pruefung> pruefungen);

    public abstract long anzahlBestandenerPruefungen(Stream<Pruefung> pruefungen, int mat);

    public abstract double durchschnittsnote(Stream<Pruefung> pruefungen, int mat);

    public abstract double durchschnittModul(Stream<Pruefung> pruefungen, String modul);

    public abstract List<Integer> endgueltigDurchgefallen(Stream<Pruefung> pruefungen);

    public abstract List<Integer> hatNochOffenenDrittenVersuch(Stream<Pruefung> pruefungen);

    @SuppressWarnings("unchecked")
    public default Stream<Pruefung> lesePruefungen() {
        try (XMLDecoder dec = new XMLDecoder(
                new BufferedInputStream(new FileInputStream(DATEI)))) {
            return ((Set<Pruefung>) dec.readObject()).stream();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
