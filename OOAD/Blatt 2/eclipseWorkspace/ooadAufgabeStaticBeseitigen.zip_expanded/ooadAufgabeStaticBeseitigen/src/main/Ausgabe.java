package main;

public class Ausgabe {
	public static void printBegruessung() {
		System.out.println("Verwaltung von Leistungspunkten");
	}

	public static void printAuswahl() {
		System.out.print("" + "0 - Programm beenden\n" + "1 - Leistung hinzufuegen\n"
				+ "2 - Leistungspunkte fuer Matrikelnummer ausgeben: ");
	}

	public static void printMatrikelInput() {
		System.out.print("Matrikelnummer: ");
	}

	public static void printFachInput() {
		System.out.print("Fach: ");
	}

	public static void printLpInput() {
		System.out.print("Leistungspunkte: ");
	}
	
	public static void printLpAusgabe(String fach, int lp) {
		System.out.printf("%s : %d\n",fach,lp);
	}
	
	public static void printSumme(int summe) {
		System.out.printf("Hat %d Leistungspunkte\n",summe);

	}
}
