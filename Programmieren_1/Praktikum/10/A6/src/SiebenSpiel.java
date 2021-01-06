public class SiebenSpiel {
	// Hauptprogramm
	public static void main(String[] args) {
		/*
		// ueber die Argumente werden die Spielernamen uebergeben
		if (args.length < 1) {
			IO.println("Bitte mindestens einen Spielernamen uebergeben");
			return;
		}
		
		
		// Spieler erzeugen
		SiebenSpieler[] spieler = new SiebenSpieler[args.length + 1];
		for (int i = 0; i < args.length; i++) {
			spieler[i] = new SiebenSpieler(args[i]);
		}
		spieler[args.length] = new SiebenProgramm("Computer");
		for(int i = 0; i < spieler.length; i++){
			System.out.println(spieler[i].toString());
		}
		*/
		SiebenSpieler[] spieler = new SiebenSpieler[2];
		spieler[0] = new SiebenProgramm("Cpu1");
		spieler[1] = new SiebenProgramm("Cpu2");
		// Spiel durchfuehren
		spielen(spieler);
	}

	// Durchfuehrung eines kompletten Spiels mit den
	// uebergebenen Spielern
	public static void spielen(SiebenSpieler[] spieler) {
		int anzahlAusgeschieden = 0;
		int zahl = 0; // begonnen wird mit der Zahl 0
		hauptschleife: while (zahl < 10000) {
			// ab 10000 wird noch eine Runde gespielt
			// alle Spieler kommen nacheinander an die Reihe
			for (int i = 0; i < spieler.length; i++) {
				if (!spieler[i].istAusgeschieden()) {
					int ergebnis = spieler[i].naechsteZahl(zahl);
					if (!check(zahl, ergebnis)) {
						spieler[i].ausscheiden();
						IO.println("Falsch! " + spieler[i] + " ist ausgeschieden!");
						anzahlAusgeschieden++;
						if (anzahlAusgeschieden == spieler.length - 1) {
							// alle bis auf einen Spieler sind ausgeschieden
							break hauptschleife; // Spielzuende
						}
					} else { // eingegebene Zahl war korrekt
						zahl = ergebnis; // zahl hochsetzen
					}
				}
			}
		}
		gibSiegerBekannt(spieler);
	}

	// Bekanntgabe der/des Siegers (wer nicht ausgeschieden ist)
	public static void gibSiegerBekannt(SiebenSpieler[] spieler) {
		for (int i = 0; i < spieler.length; i++) {
			if (!spieler[i].istAusgeschieden()) {
				IO.println(spieler[i] + " ist Sieger!");
			}
		}
	}

	/**
	 * Ueberpruefung eines Spielzugs
	 *
	 * @param aktuelleZahl
	 *            die aktuelle Zahl des Spiels
	 * @param gelieferteZahl
	 *            die vom Spieler angegebene Zahl
	 * @return genau dann true, wenn gelieferteZahl die naechst hoehere Zahl
	 *         nach aktuelleZahl ist, die keine Ziffer 7 enthaelt und nicht
	 *         durch 7 teilbar ist
	 */
	public static boolean check(int aktuelleZahl, int gelieferteZahl) {
		int rightAns = aktuelleZahl + 1;
		while (rightAns % 7 == 0 || Integer.valueOf(rightAns).toString().contains("7")) {
			rightAns++;
		}
		return rightAns == gelieferteZahl;
	}
}

class SiebenSpieler {
	private String name;
	private boolean ausgeschieden;

	public SiebenSpieler(String name) {
		this.name = name;
		this.ausgeschieden = false;
	}

	public String toString() {
		return this.name;
	}

	public int naechsteZahl(int vorherigeZahl) {
		return IO.readInt(this + ": Zahl nach " + vorherigeZahl + ":");
	}

	public void ausscheiden() {
		this.ausgeschieden = true;
	}

	public boolean istAusgeschieden() {
		return this.ausgeschieden;
	}
}