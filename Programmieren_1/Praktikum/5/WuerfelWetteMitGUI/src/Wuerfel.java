/**
 * Simulation eines 6-seitigen Würfels Die oben liegende Zahl ist die gewürfelte
 * Zahl
 * 
 * TODO Verallgemeinerung auf beliebige "Würfel"
 * 
 * @author Friedhelm Tappe, Prof. Dr.-Ing. Heiko Tapken
 * @version 25.10.2014
 *
 */

public class Wuerfel {

	private int gewuerfelteZahl;

	public Wuerfel() {
		this.wuerfeln();
	}

	public Wuerfel(int oben) {
		this.gewuerfelteZahl = oben;
	}

	public void wuerfeln() {
		gewuerfelteZahl = (int) (Math.random() * 6) + 1;
		// weisen Sie der Variablen gewuerfelteZahl wir ein zufälliger Wert
		// zwischen 1 und 6 (Integer) zu

	}

	public int getOben() {
		return gewuerfelteZahl;
	}

}
