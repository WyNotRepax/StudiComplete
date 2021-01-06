package main;

import builder.MitarbeiterBuilder;
import entity.Fachgebiet;
import entity.Mitarbeiter;

public class Main {
    public static void main(String[] s){
        Mitarbeiter tmp = MitarbeiterBuilder
                    .createBuilder()
                    .vorname("Murat")
                    .nachname("Meier")
                    .mitFachgebiet(Fachgebiet.C)
                    .mitFachgebiet(Fachgebiet.JAVA)
                    .build();
        System.out.println(tmp);
    }
}
