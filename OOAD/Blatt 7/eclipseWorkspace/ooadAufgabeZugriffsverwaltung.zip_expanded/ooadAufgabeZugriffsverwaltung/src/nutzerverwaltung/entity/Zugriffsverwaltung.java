/*
 * Author: Benno Steinkamp
 * Datum: 30.11.2020
 * */
package nutzerverwaltung.entity;

import java.util.ArrayList;
import java.util.List;

public class Zugriffsverwaltung {
	private Nutzer aktuellerNutzer;
	private List<Nutzer> nutzer;

	public Zugriffsverwaltung() {
		aktuellerNutzer = null;
		nutzer = new ArrayList<Nutzer>();
		nutzer.add(new Systemadministrator("admin", "admin"));
	}

	public boolean authentifizieren(String login, String passwort) {
		for (Nutzer n : nutzer) {
			if (login.equals(n.getLogin())) {
				if (passwort.equals(n.getPasswort())) {
					aktuellerNutzer = n;
					return true;
				}
				return false;
			}
		}
		return false;
	}

	public boolean entwicklerHinzufuegen(String login, String passwort) {
		if (aktuellerNutzer == null || !aktuellerNutzer.darfNutzerAnlegen()) {
			return false;
		}
		nutzer.add(new Entwickler(login, passwort));
		return true;
	}

	public boolean loginAendern(String altesLogin, String neuesLogin) {
		if (aktuellerNutzer == null || !aktuellerNutzer.darfNutzerAnlegen()) {
			return false;
		}
		for (Nutzer n : nutzer) {
			if (altesLogin.equals(n.getLogin())) {
				n.setLogin(neuesLogin);
				return true;
			}
		}
		return false;
	}

	public Nutzer getAktuellerNutzer() {
		return aktuellerNutzer;
	}

	public void setAktuellerNutzer(Nutzer aktuellerNutzer) {
		this.aktuellerNutzer = aktuellerNutzer;
	}

	public List<Nutzer> getNutzer() {
		return nutzer;
	}

	public void setNutzer(List<Nutzer> nutzer) {
		this.nutzer = nutzer;
	}

	public void nutzerAnzeigen() {
		for (Nutzer n : nutzer) {
			System.out.printf("%n %n %b %b %b", n.getLogin(), n.getPasswort(), n.darfNutzerAnlegen(),
					n.darfTabellenBearbeiten(), n.darfDatenBearbeiten());
		}
	}

	public boolean passwortAendern(String altesPasswort, String neuesPasswort) {
		if (aktuellerNutzer == null || !altesPasswort.equals(aktuellerNutzer.getPasswort())) {
			return false;
		}
		aktuellerNutzer.setPasswort(neuesPasswort);
		return true;
	}

	public boolean projektadministratorHinzufuegen(String login, String passwort) {
		if (aktuellerNutzer == null || !aktuellerNutzer.darfNutzerAnlegen()) {
			return false;
		}
		nutzer.add(new Projektadministrator(login, passwort));
		return true;
	}

	public boolean systemadministratorHinzufuegen(String login, String passwort) {
		if (aktuellerNutzer == null || !aktuellerNutzer.darfNutzerAnlegen()) {
			return false;
		}
		nutzer.add(new Systemadministrator(login, passwort));
		return true;
	}
}
