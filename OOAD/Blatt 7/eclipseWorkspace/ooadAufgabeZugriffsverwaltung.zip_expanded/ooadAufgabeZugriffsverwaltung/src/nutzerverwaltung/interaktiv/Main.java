package nutzerverwaltung.interaktiv;

import java.util.Locale;

public class Main {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en","US")); 
		(new Zugriffsdialog()).nutzungsdialog();
	}

}
