/**
 * Die Klasse stellt eine Repraesentation des Hamster-Territoriums dar. Sie
 * definiert ausschliesslich Klassenmethoden. Diese dienen zum Abfragen
 * bestimmter Zustandswerte des aktuellen Territoriums.
 * 
 * @author Dietrich Boles (Universitaet Oldenburg)
 * @version 2.5.2 (11.04.2008)
 * 
 */
public class Territorium {

	/**
	 * private-Konstruktor: es koennen keine Instanzen der Klasse erzeugt werden
	 */
	private Territorium() {
	}

	/**
	 * liefert die Anzahl an Reihen im Territorium
	 * 
	 * @return die Anzahl an Reihen im Territorium
	 */
	public synchronized static int getAnzahlReihen() {
		return 0;
	}

	/**
	 * liefert die Anzahl an Spalten im Territorium
	 * 
	 * @return die Anzahl an Spalten im Territorium
	 */
	public synchronized static int getAnzahlSpalten() {
		return 0;
	}

	/**
	 * ueberprueft, ob sich auf der Kachel (reihe/spalte) eine Mauer befindet;
	 * es wird genau dann true geliefert, wenn sich auf der angegebenen Kachel
	 * eine Mauer befindet oder wenn sich die angegebenen Werte ausserhalb des
	 * Territoriums befinden
	 * 
	 * @param reihe
	 *            Reihe der Kachel
	 * @param spalte
	 *            Spalte der Kachel
	 * @return true geliefert, wenn sich auf der angegebenen Kachel eine Mauer
	 *         befindet oder wenn sich die angegebenen Werte ausserhalb des
	 *         Territoriums befinden; sonst false
	 */
	public synchronized static boolean mauerDa(int reihe, int spalte) {
		return true;
	}

	/**
	 * liefert die Gesamtzahl an Koernern, die im Territorium auf Kacheln
	 * herumliegen
	 * 
	 * @return die Gesamtzahl an Koernern, die im Territorium auf Kacheln
	 *         herumliegen
	 */
	public synchronized static int getAnzahlKoerner() {
		return 0;
	}

	/**
	 * liefert die Anzahl an Koernern auf der Kachel (reihe/spalte) oder 0,
	 * falls die Kachel nicht existiert oder durch eine Mauer blockiert ist
	 * 
	 * @param reihe
	 *            Reihe der Kachel
	 * @param spalte
	 *            Spalte der Kachel
	 * @return die Anzahl an Koernern auf der Kachel (reihe/spalte) oder 0,
	 *         falls die Kachel nicht existiert oder durch eine Mauer blockiert
	 *         ist
	 */
	public synchronized static int getAnzahlKoerner(int reihe, int spalte) {
		return 0;
	}

	/**
	 * liefert die Gesamtzahl an erzeugten und initialisierten Hamstern im
	 * Territorium (inkl. dem Standard-Hamster)
	 * 
	 * @return die Gesamtzahl an erzeugten und initialisierten Hamstern im
	 *         Territorium
	 */
	public synchronized static int getAnzahlHamster() {
		return 0;
	}

	/**
	 * liefert alle erzeugten und initialisierten Hamster im Territorium (inkl.
	 * dem Standard-Hamster)
	 * 
	 * @return alle erzeugten und initialisierten Hamster im Territorium
	 */
	public static Hamster[] getHamster() {
		return null;
	}

	/**
	 * liefert die Anzahl an Hamstern auf der Kachel (reihe/spalte) oder 0,
	 * falls die Kachel nicht existiert oder durch eine Mauer blockiert ist
	 * 
	 * @param reihe
	 *            Reihe der Kachel
	 * @param spalte
	 *            Spalte der Kachel
	 * @return die Anzahl an Hamstern auf der Kachel (reihe/spalte) oder 0,
	 *         falls die Kachel nicht existiert oder durch eine Mauer blockiert
	 *         ist
	 */
	public synchronized static int getAnzahlHamster(int reihe, int spalte) {
		return 0;
	}

	/**
	 * liefert alle erzeugten und initialisierten Hamster, die aktuell auf der
	 * Kachel (reihe/spalte) stehen (inkl. dem Standard-Hamster)
	 * 
	 * @param reihe
	 *            Reihe der Kachel
	 * @param spalte
	 *            Spalte der Kachel
	 * @return alle erzeugten und initialisierten Hamster, die aktuell auf der
	 *         Kachel (reihe/spalte) stehen
	 */
	public synchronized static Hamster[] getHamster(int reihe, int spalte) {
		return null;
	}

	/**
	 * liefert ein Objekt, das als Sperr-Objekt fuer Aktionen auf der
	 * entsprechenden Kachel genutzt werden kann (und soll)
	 * 
	 * @param reihe
	 *            Reihe der Kachel
	 * @param spalte
	 *            Spalte der Kachel
	 * @return ein der Kachel zugeordnetes Sperr-Objekt
	 */
	public synchronized static Object getKachel(int reihe, int spalte) {
		return null;
	}

}
