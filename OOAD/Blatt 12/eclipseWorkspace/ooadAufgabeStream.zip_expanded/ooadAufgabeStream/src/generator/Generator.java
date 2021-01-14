package generator;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entities.Modul;
import entities.Pruefung;
import entities.Student;

public class Generator {

    private final static String DATEI = "./data.xml";
    private List<Student> stud = new ArrayList<>();
    private List<Modul> module = new ArrayList<>();
    private Set<Pruefung> pruef = new HashSet<>();

    private Pruefung pruefung(int m, int s, int n, int v) {
        return new Pruefung(this.module.get(m), this.stud.get(s), n, v);
    }

    public void generate() {
        String[] wichtig = {"Programmierung 1", "Programmierung 2"
                , "Datenbanken", "Algorithmen", "OOAD"
                , "Verteilte Systeme", "Theoretische Informatik"
                , "SW-Projekt", "Komponenten"};
        int i = 0;
        for (String s : wichtig) {
            this.module.add(new Modul(++i, s));
        }

        i = 100;
        String[] studi = {"Sergej", "Leila", "Ute", "Uwe"};
        for (String s : studi) {
            this.stud.add(new Student(i++, s));
        }

        this.pruef.add(pruefung(0, 0, 100, 1));
        this.pruef.add(pruefung(0, 1, 200, 1));
        this.pruef.add(pruefung(0, 2, 500, 1));
        this.pruef.add(pruefung(0, 2, 230, 2));
        this.pruef.add(pruefung(0, 3, 500, 1));
        this.pruef.add(pruefung(0, 3, 500, 2));
        this.pruef.add(pruefung(0, 3, 400, 3));

        this.pruef.add(pruefung(1, 0, 170, 1));
        this.pruef.add(pruefung(1, 1, 130, 1));
        this.pruef.add(pruefung(1, 2, 500, 1));
        this.pruef.add(pruefung(1, 2, 230, 2));
        this.pruef.add(pruefung(1, 3, 500, 1));
        this.pruef.add(pruefung(1, 3, 500, 2));
        this.pruef.add(pruefung(1, 3, 500, 3)); // endgueltig durchgefallen

        this.pruef.add(pruefung(2, 0, 130, 1));
        this.pruef.add(pruefung(2, 1, 170, 1));
        this.pruef.add(pruefung(2, 2, 500, 1));
        this.pruef.add(pruefung(2, 2, 500, 2));
        this.pruef.add(pruefung(2, 3, 500, 1));
        this.pruef.add(pruefung(2, 3, 500, 2)); // offen

        this.pruef.add(pruefung(3, 0, 500, 1));
        this.pruef.add(pruefung(3, 0, 270, 2));
        this.pruef.add(pruefung(3, 1, 300, 1));
        this.pruef.add(pruefung(3, 2, 500, 1));
        this.pruef.add(pruefung(3, 2, 500, 2));
        this.pruef.add(pruefung(3, 2, 370, 3));

        this.pruef.add(pruefung(4, 0, 270, 1));
        this.pruef.add(pruefung(4, 1, 230, 1));
        this.pruef.add(pruefung(4, 2, 500, 1));
        this.pruef.add(pruefung(4, 2, 370, 2));

        this.pruef.add(pruefung(5, 0, 500, 1));
        this.pruef.add(pruefung(5, 0, 500, 2));
        this.pruef.add(pruefung(5, 0, 400, 3));
        this.pruef.add(pruefung(5, 1, 230, 1));
        this.pruef.add(pruefung(5, 2, 500, 1));
        this.pruef.add(pruefung(5, 2, 500, 2)); // offen

        this.pruef.add(pruefung(6, 0, 130, 1));
        this.pruef.add(pruefung(6, 1, 200, 1));
        this.pruef.add(pruefung(6, 2, 500, 1));
        this.pruef.add(pruefung(6, 2, 230, 2));

        this.pruef.add(pruefung(7, 0, 170, 1));
        this.pruef.add(pruefung(7, 1, 100, 1));

        this.pruef.add(pruefung(8, 2, 200, 1));

        try (XMLEncoder enc = new XMLEncoder(
                new BufferedOutputStream(new FileOutputStream(DATEI)))) {
            enc.writeObject(this.pruef);
            //for (Pruefung p: this.pruef){
            //	enc.writeObject(p);
            //}
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public Set<Pruefung> lesePruefungen(String datei) {
        try (XMLDecoder dec = new XMLDecoder(
                new BufferedInputStream(new FileInputStream(DATEI)))) {
            return (Set<Pruefung>) dec.readObject();
            //for (Pruefung p: this.pruef){
            //	enc.writeObject(p);
            //}
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Generator g = new Generator();
        g.generate();
        for (Pruefung p : g.lesePruefungen(DATEI)) {
            System.out.println(p);
        }

    }

}
