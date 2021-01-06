package main;

import java.util.Set;

import aktion.Ausfuehrung;
import zustand.S1;
import zustand.S3;
import zustand.Zustand;

public class Main {
  
  public static void main(String... s) {
    Ausfuehrung automat = new Ausfuehrung(new S1(), Set.of(new S3()));
    boolean ok = true;
    System.out.print("zu untersuchender String: ");
    String eingabe = Eingabe.leseString();
    for( int i = 0; i < eingabe.length() && ok; i++) {
      ok = ok && automat.schritt(eingabe.charAt(i));
    }
    if (ok && automat.imEndzustand()) {
      System.out.println("Eingabe akzeptiert");
    } else {
      System.out.println("Eingabe nicht akzeptiert");
    }
  }
}
