import java.io.IOException;

/**
 * 
 * @author Friedhelm Tappe
 * @version 31.07.2014
 */

public class Main {

	public static void main(String[] args) throws IOException {
		
		Wuerfel einWuerfel = new Wuerfel();
		Spiel einSpiel = new Spiel(1000, einWuerfel);
		GUI einGUISpiel = new GUI(einSpiel);
		
	}

}
