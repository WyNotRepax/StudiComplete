package main;

import java.util.List;


// TODO
// Diese Klasse ist zu erweitern, die angegebenen Methoden u. a. 
// zu ergaenzen.

public class Dialog {


    public int auswahl() {
        System.out.print("Was wollen Sie machen?\n"
                + " (0) Programm beenden\n"
                + " (1) Mitarbeiter anlegen\n"
                + " (2) Projektwhiteboard anlegen\n"
                + " (3) Abteilungswhiteboard anlegen\n"
                + " (4) Mitarbeiter bei Projektwhiteboard anmelden\n"
                + " (5) Projekt bei Abteilungswhiteboard anmelden\n"
                + " (6) Information auf Projektwhiteboard\n"
                + " (7) Information auf Abteilungswhiteboard\n"
                + " (8) Uebersicht\n"
        );
        return Eingabe.leseInt();
    }

    public void schleife() {
        int eingabe = -1;
        while (eingabe != 0) {
            eingabe = this.auswahl();
            switch (eingabe) {
                case 1: {
                    mitarbeiterHinzufuegen();
                    break;
                }
                case 2: {
                    projektwhiteboardHinzufuegen();
                    break;
                }
                case 3: {
                    abteilungswhiteboardHinzufuegen();
                    break;
                }
                case 4: {
                    mitarbeiterAnmelden();
                    break;
                }
                case 5: {
                    projektAnmelden();
                    break;
                }
                case 6: {
                    projektwhiteboardBeschreiben();
                    break;
                }
                case 7: {
                    abteilungswhiteboardBeschreiben();
                    break;
                }
                case 8: {
                    uebersichtAnzeigen();
                    break;
                }                
            }
        }
    }

    public int auswahl(List<?> liste) {
        System.out.println(" (0) Aktion abbrechen");
        for (int i = 0; i < liste.size(); i++) {
            System.out.println(" (" + (i + 1) + ") " + liste.get(i));
        }
        System.out.print("Welches Element: ");
        return Eingabe.leseInt() - 1;
    }

    public void mitarbeiterHinzufuegen() {
        System.out.print("Name: ");
        String name = Eingabe.leseString();
        System.out.print("Mitarbeiternummer: ");
        int minr = Eingabe.leseInt();
        // TODO
        
    }

    public void projektwhiteboardHinzufuegen() {
        System.out.print("Projekt: ");
        String projekt = Eingabe.leseString();
        // TODO
        
    }

    public void abteilungswhiteboardHinzufuegen() {
        System.out.print("Abteilung: ");
        String abteilung = Eingabe.leseString();
        System.out.print("Abteilungsnummer: ");
        int abtnr = Eingabe.leseInt();
        // TODO
        
    }

    public void mitarbeiterAnmelden() {
        System.out.println("Welcher Mitarbeiter?");
        // TODO        
        
        
        System.out.println("Bei welchem Projekt?");
        // TODO
    }

    public void projektAnmelden() {
        System.out.println("Welches Projekt?");
        // TODO
        
        
        System.out.println("Bei welcher Abteilung?");
        // TODO
    }

    public void projektwhiteboardBeschreiben() {
        System.out.println("Welches Projekt?");
        // TODO
        
        System.out.print("Projektnachricht: ");
        String nachricht = Eingabe.leseString();
        // TODO

    }

    public void abteilungswhiteboardBeschreiben() {
        System.out.println("Welche Abteilung?");
        // TODO
        
        System.out.print("Abteilungsnachricht: ");
        String nachricht = Eingabe.leseString();
        // TODO
        
    }
    
    public void uebersichtAnzeigen(){
        // TODO
        
    }
}
