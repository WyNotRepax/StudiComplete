/*
 * Author: Benno Steinkamp
 * Datum: 30.11.2020
 * */
package nutzerverwaltung.entity;

public abstract class Nutzer {
	private String login;
	private String passwort;

	public Nutzer() {
	}

	public Nutzer(String login, String passwort) {
		this.login = login;
		this.passwort = passwort;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public abstract boolean darfDatenBearbeiten();

	public abstract boolean darfTabellenBearbeiten();

	public abstract boolean darfNutzerAnlegen();
}
