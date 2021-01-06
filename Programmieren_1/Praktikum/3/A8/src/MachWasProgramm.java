public class MachWasProgramm {
	/**
	 * Ein super tolles Programm zur Berechnung von Zahlen
	 **/

	public static boolean istTolleZahl = true;

	public static void main(String arg[]) {
		int n = 0; // n wird definiert und auf 0 gesetzt
		int x; // x wird definiert aber nicht initialisiert
		int j = 0; // j wird definiert und auf 0 gesetzt
		System.out.print("tolle Zahlen berechnen bis: "); // Gibt "tolle Zahlen berechnen bis: " auf der Konsole aus
		x = IO.readInt(); // Liest einen Integer wert vom Nutzer ein und setzt x auf diesen Wert
		while (n <= x) { // Solange n kleiner als x ist
			int z = 2; // z wird definiert und auf 2 gesetzt
			istTolleZahl = true; // globale Variable istTolleZahl wird auf true gesetzt
			while (z * z <= n) { // solange z^2 kleiner oder gleich n ist
				boolean temp = true; // temp wird definiert u
				if (n % z == 0) { // wenn n durch z teilbar ist
					istTolleZahl = false; // istTolleZahls wird auf false gesetzt
					temp = false; // temp wird auf false gesetzt
				}
				if (!temp) { // Wird ausgeführt wen das if statement ausgeführt wird
					z = (int) Math.sqrt(n); // Berechnet die abgerundete wurzel von n
				}
				z++; // z wird um Eins erhöht
			}
			if (istTolleZahl == true && n != 0 && n != 1) { // Wird ausgeführt wenn istTolleZahl wahr ist und n nicht
															// null oder 1
				System.out.println(n + " ist tolle Zahl"); // Gibt n aus
				j++; // j wird um Eins erhöht
			}
			n++; // n wird um Eins erhöht
		}
		System.out.println("Gesamtzahl der tollen Zahlen von 0 bis " + x + ": " + j); // Ausgabe am Ende des Progamms
	}
}