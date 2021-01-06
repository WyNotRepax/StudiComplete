package main;
/* Einfache Klasse, um direkt Eingaben von der Tastatur/
 * Konsole zu lesen. Die Methoden fangen Fehler ab und geben bei
 * falschen Eingaben "Standardwerte", genauer den leeren
 * String, -1 und false, zur&uuml;ck.
 * Benutzung auf eigene Gefahr.
 * @author kleuker
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class EinUndAusgabe {

  /**
   * Konstruktor zur Erzeugung eines Objekts zur Ein- und Ausgabe.
   */
  public EinUndAusgabe() {
  }

  /**
   * Methode zum Lesen eines Textes von der Konsole, der &uuml;ber die
   * Tastatur eingegeben wird. Die Eingabe endet mit der Return-Taste
   * und darf Leerzeichen enthalten.
   *
   * @return eingegebener Text
   */
  public String leseString() {
      String ergebnis;

      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      try {
          ergebnis = in.readLine();
      } catch (IOException e) {
          ergebnis = "";
      }
      return ergebnis;
  }
  
  /**
   * Methode zum Lesen einer ganzen Zahl von der Konsole, die
   * &uuml;ber die Tastatur eingegeben wird. Die Eingabe endet mit der
   * Return-Taste. Sollte es sich bei der Eingabe um keinen
   * g&uuml;ltigen Wert handeln, wird -1 zur&uuml;ckgegeben.
   *
   * @return eingegebene Zahl
   */
  public int leseInteger() {
    int ergebnis;
    try {
      ergebnis = Integer.decode(this.leseString());
    } catch (NumberFormatException e) {
      ergebnis = -1;
    }

    return ergebnis;
  }

  /**
   * Methode zum Lesen einer Float-Zahl von der Konsole, die &uuml;ber
   * die Tastatur eingegeben wird. Die Eingabe endet mit der
   * Return-Taste. Sollte es sich bei der Eingabe um keinen
   * g&uuml;ltigen Wert handeln, wird -1 zur&uuml;ckgegeben.
   *
   * @return eingegebene Zahl
   */
  public float leseFloat() {
    float ergebnis;
    try {
      ergebnis = Float.parseFloat(this.leseString());
    } catch (NumberFormatException e) {
      ergebnis = -1f;
    }

    return ergebnis;
  }

  /**
   * Methode zum Lesen einer Double-Zahl von der Konsole, die
   * &uuml;ber die Tastatur eingegeben wird. Die Eingabe endet mit der
   * Return-Taste. Sollte es sich bei der Eingabe um keinen
   * g&uuml;ltigen Wert handeln, wird -1 zur&uuml;ckgegeben.
   *
   * @return eingegebene Zahl
   */
  public double leseDouble() {
    double ergebnis;
    try {
      ergebnis = Double.parseDouble(this.leseString());
    } catch (NumberFormatException e) {
      ergebnis = -1d;
    }

    return ergebnis;
  }

  /**
   * Methode zum Lesen eines Wahrheitswertes von der Konsole, der
   * &uuml;ber die Tastatur eingegeben wird. Die Eingabe endet mit der
   * Return-Taste. Sollte es sich bei der Eingabe nicht um "true"
   * handeln, wird false zur&uuml;ckgegeben.
   *
   * @return eingegebener Wahrheitswert
   */
  public boolean leseBoolean() {
    boolean ergebnis;
    try {
      ergebnis = Boolean.parseBoolean(this.leseString());
    } catch (Exception e) {
      ergebnis = false;
    }

    return ergebnis;
  }

  /**
   * Methode zur Ausgabe eines &uuml;bergebenen Textes. Es wird kein
   * Zeilenumbruch angeh&auml;ngt.
   *
   * @param obj auszugebendes Objekt (nutzt jeweiliges toString)
   */
  public void ausgeben(Object obj) {
    System.out.print(obj.toString());
  }

  /**
   * Methode zur C-formatierten Ausgabe verschiedener Variablen und
   * Textelemente
   *
   * @param text auszugebender Text, der Spezialzeichen wie %d als
   * Platzhalter f&uuml;r auszugebene Variablen enthalten kann
   * @param objekte Objekte, die an Stelle der Platzhalter im Text
   * ausgegeben werden sollen
   */
  public void formatiertAusgeben(String text,
          Object... objekte) {
    System.out.printf(text, objekte);
  }

  /**
   * Methode zur Erzeugung einer ganzahligen Zufallszahl zwischen
   * (einschlie&szlig;lich) den &uuml;bergebenen Grenzen. Es wird
   * erwartet und nicht gepr&uuml;ft, dass der Endwert nicht kleiner
   * als der Startwert ist.
   *
   * @param start minimal m&ouml;glicher Zufallswert
   * @param ende maximal m&ouml;glicher Zufallswert
   * @return zuf&auml;lliger Wert zwischen start und ende (auch diese
   * beiden Werte sind m&ouml;glich
   */
  public Integer zufall(Integer start, Integer ende) {
    return start
            + (int) (Math.random() * (ende - start + 1));
  }

// rein zu Testzwecken hier stehen gelassen, kann geloescht werden
//  public static void main(String[] s) {
//    EinUndAusgabe eingabe = new EinUndAusgabe();
//    int ein = 0;
//    while (ein != -1) {
//      System.out.print("Text eingeben: ");
//      System.out.println("Eingegeben wurde:" + eingabe.leseString());
//      System.out.print("Float eingeben: ");
//      System.out.println("Eingegeben wurde:" + eingabe.leseFloat());
//      System.out.print("Double eingeben: ");
//      System.out.println("Eingegeben wurde:" + eingabe.leseDouble());
//      System.out.print("Boolean eingeben: ");
//      System.out.println("Eingegeben wurde:" + eingabe.leseBoolean());
//      System.out.print("Ganze Zahl eingeben (Abbruch mit -1): ");
//      ein = eingabe.leseInteger();
//      System.out.println("Eingegeben wurde: " + eingabe);
//    }
//  }
}
