
public class Zahlenfolgenerweiterung extends Frage {
	static String text = "Identifizieren Sie die Zahlenfolge und geben Sie die n�chste Zahl der Folge ein!";
	
	int[] zahlen;
	int antwort;
	Zahlenfolgenerweiterung(int[] zahlen, int antwort, int punkte) {
		super(text, punkte);
		this.zahlen = zahlen;
		this.antwort = antwort;
	}
	
	@Override
	void frageStellen(){
		super.frageStellen();
		for(int i = 0; i < zahlen.length; i++){
			if( i != 0){
				IO.print(", ");
			}
			IO.print(zahlen[i]);
		}
	}

	@Override
	void frageBeantworten(Pruefling person) {
		int antwort = IO.readInt("Auswahl: ");
		if (antwort == this.antwort) {
			IO.println("Richtige Antwort: " + this.punkte + "Punkte");
			person.neuePunkte(this.punkte);
		} else {
			IO.println("Falsche Antwort: 0 Punkte!");
			IO.println("Richtig Antwort ist " + this.antwort);
		}
	}

}
