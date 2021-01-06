package nutzerverwaltung.entity;

public class Systemadministrator extends Nutzer {
	public Systemadministrator() {
		super();
	}

	public Systemadministrator(String login, String passwort) {
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
		return true;
	}
}
