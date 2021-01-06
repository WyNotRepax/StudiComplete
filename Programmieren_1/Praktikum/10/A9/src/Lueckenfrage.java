
public class Lueckenfrage extends Frage{
	String textVorLuecke;
	String luecke;
	String textNachLuecke;

	Lueckenfrage(String textVorLuecke, String luecke,String textNachluecke, int punkte) {
		super(textVorLuecke + " ___ " + textNachluecke, punkte);
		this.textVorLuecke = textVorLuecke;
		this.luecke = luecke;
		this.textNachLuecke = textNachluecke;
		// TODO Auto-generated constructor stub
	}

	@Override
	void frageBeantworten(Pruefling person) {
		String antwort = IO.readString("Antwort: ");
		if (antwort.equals(luecke)) {
			IO.println("Richtige Antwort: " + this.punkte + "Punkte");
			person.neuePunkte(this.punkte);
		} else {
			IO.println("Falsche Antwort: 0 Punkte!");
			IO.println("Richtig Antwort ist " + luecke);
			IO.println(textVorLuecke + " " + luecke + " " + textNachLuecke);
		}
		
	}
}
