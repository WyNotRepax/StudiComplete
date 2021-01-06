/**
 * 
 * Erzeugen eines Spielfeldes der Größe sizeXY
 * Die Felder werden ausgehend von der Lösung zufällig verschoben, so
 * dass eine lösbare Ausgangssituation entsteht.
 * Das SpielfeldGUI wird mit einer Referenz auf das Spielfeld instanziiert.
 * 
 *  
 * @author Friedhelm Tappen, Prof. Dr.-Ing. Heiko Tapken
 * @version 25.10.2014
 */
public class Main {

	static final int sizeXY = 5;

	static int xfrei;
	static int yfrei;

	public static void main(String[] args) {
		Spielfeld einSpielfeld = new Spielfeld(sizeXY);
		einSpielfeld.randomInit(sizeXY*1000000);
		GUI spielfeldGUI = new GUI(einSpielfeld);	
		spielfeldGUI.setVisible(true);				
	}

}

