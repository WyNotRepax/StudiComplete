package de.hsos.prog3.bsteinka.ab01;

import java.util.HashSet;
import java.util.Set;

public class App {

    public static void main(String[] args) {
        Set<Nachbar> nachbarn = new HashSet<Nachbar>();

        nachbarn.add(new Nachbar("Vorname1", "Nachname1"));
        nachbarn.add(new Nachbar("DoppelterVorname", "Nachname2"));
        nachbarn.add(new Nachbar("DoppelterVorname", "Nachname3"));
        nachbarn.add(new Nachbar("Vorname4", "DoppelterNachname"));
        nachbarn.add(new Nachbar("Vorname5", "DoppelterNachname"));
        nachbarn.add(new Nachbar("Vorname1", "Nachname1"));

        String out = "Hallo ";
        for (Nachbar n : nachbarn) {
            out += n.toString() + ", ";
        }
        out = out.substring(0,out.length()-2);
        System.out.println(out);
    }
}
