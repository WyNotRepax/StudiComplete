
public class Ordnungsfrage extends Frage {

	String[] objekte;
	int[] indices;

	Ordnungsfrage(String text, int punkte, String[] objekte, int[] indices) {
		super(text, punkte);
		this.objekte = objekte;
		this.indices = indices;
	}

	@Override
	void frageStellen() {
		super.frageStellen();
		for (int i = 0; i < objekte.length; i++) {
			IO.println("(" + i + ") : " + objekte[i]);
		}
	}

	@Override
	void frageBeantworten(Pruefling person) {
		int[] eingabe = new int[indices.length];
		for (int i = 0; i < indices.length; i++) {
			eingabe[i] = IO.readInt((i + 1) + ". :");
			while (true) {
				boolean unique = true;
				for (int n = 0; n < i; n++) {
					if (eingabe[n] == eingabe[i]) {
						unique = false;
						break;
					}
				}
				if (unique) {
					break;
				} else {
					eingabe[i] = IO.readInt("Antwort bereits gegeben! Auswahl:");
				}
			}
		}
		int richtig = 0;
		for (int i = 0; i < indices.length; i++) {
			if (indices[i] == eingabe[i]) {
				richtig++;
			}
		}
		if (indices.length == richtig) {
			IO.println("Richt. Antw.: " + this.punkte + " Punkte");
			person.neuePunkte(this.punkte);
		} else {
			IO.println("Falsche Antwort: " + ((this.punkte * indices.length) / richtig) + " Punkte");
			person.neuePunkte(((this.punkte * indices.length) / richtig));
		}
	}

}
