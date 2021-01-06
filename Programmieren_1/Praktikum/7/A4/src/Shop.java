public class Shop {
	public static void main(String[] args) {
		double buchPreis = IO.readDouble("Buchpreis: ");
		double dvdPreis = IO.readDouble("DVD-Preis: ");
		Haendler buchHaendler = new Haendler(buchPreis);
		Haendler dvdHaendler = new Haendler(dvdPreis);
		char weiter = 0;
		do {
			char auswahl = IO.readChar("Buch oder DVD kaufen (b/d)?");
			int anzahl = IO.readInt("Anzahl Produkte: ");
			if (auswahl == 'b') {
				buchHaendler.kaufen(anzahl);
			} else {
				dvdHaendler.kaufen(anzahl);
			}
			weiter = IO.readChar("weiter einkaufen(j/n): ");
		} while (weiter == 'j');
		double einnamen = buchHaendler.liefereEinnahmen();
		System.out.println("Einnahmen des Buchhaendlers = " + einnamen);
		einnamen = dvdHaendler.liefereEinnahmen();
		System.out.println("Einnahmen des DVD-Haendlers = " + einnamen);
	}
}