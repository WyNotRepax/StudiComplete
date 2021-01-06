/*
 * Author: Benno Steinkamp
 * Datum: 30.11.2020
 * */
package nutzerverwaltung.entity;

public class Projektadministrator extends Nutzer {
	public Projektadministrator() {
		super();
	}

	public Projektadministrator(String login, String passwort) {
		super(login, passwort);
	}

	@Override
	public boolean darfDatenBearbeiten() {
		return true;
	}

	@Override
	public boolean darfTabellenBearbeiten() {
		return true;
	}

	@Override
	public boolean darfNutzerAnlegen() {
		return false;
	}
}
