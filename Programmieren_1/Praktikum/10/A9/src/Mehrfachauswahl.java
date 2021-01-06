
public class Mehrfachauswahl extends Frage {
	String[] antworten;
	int[] richtigIndex;

	Mehrfachauswahl(String text, int punkte, String[] antworten, int[] richtigIndex) {
		super(text, punkte);
		this.antworten = antworten;
		this.richtigIndex = richtigIndex;
	}

	@Override
	void frageStellen() {
		super.frageStellen();
		for (int f = 0; f < this.antworten.length; f++) {
			IO.println("(" + f + "): " + this.antworten[f]);
		}
	}

	@Override
	void frageBeantworten(Pruefling person) {
		int anzahlRichtig = IO.readInt("Anzahl Richtige Antworten:");

		int[] antworten = new int[anzahlRichtig];
		for (int i = 0; i < anzahlRichtig; i++) {
			antworten[i] = IO.readInt("Auswahl: ");
			while (true) {
				boolean unique = true;
				for (int n = 0; n < i; n++) {
					if (antworten[n] == antworten[i]) {
						unique = false;
						break;
					}
				}
				if (unique) {
					break;
				} else {
					antworten[i] = IO.readInt("Antwort bereits gegeben! Auswahl:");
				}
			}
		}
		int relpunkte = 0;
		for (int i = 0; i < antworten.length; i++) {
			boolean richtig = false;
			for (int n = 0; n < richtigIndex.length; n++) {
				if (antworten[i] == richtigIndex[n]) {
					richtig = true;
					break;
				}
			}
			if (richtig) {
				relpunkte += 1;
			} else {
				relpunkte -= 1;
			}
			
		}
		if (richtigIndex.length > 0) {
			System.err.println(relpunkte + " " + this.punkte + " " + richtigIndex.length);
			relpunkte = (relpunkte * this.punkte) / richtigIndex.length;
		} else {
			relpunkte = this.punkte;
		}
		if (relpunkte > 0) {
			IO.println("Richtige Antwort: " + relpunkte + "Punkte");
			person.neuePunkte(relpunkte);
		} else {
			IO.println("Falsche Antwort: 0 Punkte!");
		}
		IO.print("Richtig Antworten sind: ");
		for (int i = 0; i < richtigIndex.length; i++) {
			if (i != 0) {
				IO.print(", ");
			}
			IO.print(richtigIndex[i]);
		}
		IO.println();

	}
}
